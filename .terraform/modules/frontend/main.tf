resource "kubernetes_service" "frontend" {
  metadata {
    name      = "printer3d-frontend-${var.environment}-svc"
    namespace = var.namespace
  }

  spec {
    selector = {
      app         = "printer3d-frontend"
      version     = var.artifactVersion
      environment = var.environment
    }

    type = "NodePort"

    port {
      protocol    = "TCP"
      port        = var.port
      target_port = var.port
      node_port   = 30201
    }
  }
}

resource "kubernetes_config_map" "frontend" {
  metadata {
    name      = "printer3d-frontend-environment"
    namespace = var.namespace
  }

  data = {
    NODE_ENV          = var.environment
    APP_NAME          = "Tetsuo3D-UI"
    PORT              = tostring(var.port)
    ENVIRONMENT       = var.environment
    NODE_VERSION      = "10.24.1"
    PRODUCTION        = var.environment == "production" ? "true" : "false"
    VERSION           = var.artifactVersion
    MODULE_NAME       = "Printer3D-frontend"
    IMAGE_NAME        = "printer3d-frontend"
    APPLICATION_HOME  = "/dist/Printer3D-frontend"
    BACKEND_PROXY     = "http://printer3d-backend-${var.environment}-svc:${var.port}/"
  }
}

resource "kubernetes_deployment" "frontend" {
  metadata {
    name      = "printer3d-frontend"
    namespace = var.namespace
    labels = {
      app         = "printer3d-frontend"
      version     = var.artifactVersion
      environment = var.environment
    }
  }

  spec {
    selector {
      match_labels = {
        app = "printer3d-frontend"
      }
    }

    template {
      metadata {
        labels = {
          app         = "printer3d-frontend"
          version     = var.artifactVersion
          environment = var.environment
        }
      }

      spec {
        container {
          name  = "printer3d-frontend"
          image = "${var.image_name}:${var.artifactVersion}"

          dynamic "env" {
            for_each = kubernetes_config_map.frontend.data
            content {
              name = env.key
              value_from {
                config_map_key_ref {
                  name = kubernetes_config_map.frontend.metadata[0].name
                  key  = env.key
                }
              }
            }
          }

          port {
            container_port = var.port
          }

          image_pull_policy = "Always"
        }

        service_account_name = "default"
      }
    }
  }
} 
