resource "kubernetes_service" "backend" {
  metadata {
    name      = "printer3d-backend-${var.environment}-svc"
    namespace = var.namespace
  }

  spec {
    selector = {
      app         = "printer3d-backend"
      version     = var.artifactVersion
      environment = var.environment
    }

    type = "NodePort"

    port {
      protocol    = "TCP"
      port        = var.port
      target_port = var.port
      node_port   = 30202
    }
  }
}

resource "kubernetes_config_map" "backend" {
  metadata {
    name      = "printer3d-backend-environment"
    namespace = var.namespace
  }

  data = {
    PORT                      = tostring(var.port)
    ENVIRONMENT              = var.environment
    VERSION                  = var.artifactVersion
    MODULE_NAME              = "Printer3D-backend"
    IMAGE_NAME               = "printer3d-backend"
    SPRING_DATASOURCE_URL    = "jdbc:postgresql://postgres-sv:5432/${var.environment}"
    SPRING_DATASOURCE_USERNAME = "printer3d"
    SPRING_DATASOURCE_PASSWORD = "z.iliada.2020"
  }
}

resource "kubernetes_deployment" "backend" {
  metadata {
    name      = "printer3d-backend"
    namespace = var.namespace
    labels = {
      app         = "printer3d-backend"
      version     = var.artifactVersion
      environment = var.environment
    }
  }

  spec {
    selector {
      match_labels = {
        app = "printer3d-backend"
      }
    }

    template {
      metadata {
        labels = {
          app         = "printer3d-backend"
          version     = var.artifactVersion
          environment = var.environment
        }
      }

      spec {
        container {
          name  = "printer3d-backend"
          image = "${var.image_name}:${var.artifactVersion}"

          dynamic "env" {
            for_each = kubernetes_config_map.backend.data
            content {
              name = env.key
              value_from {
                config_map_key_ref {
                  name = kubernetes_config_map.backend.metadata[0].name
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
