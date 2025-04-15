# Provider configuration
provider "kubernetes" {
  # Add your cluster configuration here
}

# Backend Resources
resource "kubernetes_service" "backend" {
  metadata {
    name      = "printer3d-backend-production-svc"
    namespace = var.namespace
  }

  spec {
    selector = {
      app         = "printer3d-backend"
      version     = var.version
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
    VERSION                  = var.version
    MODULE_NAME              = "Printer3D-backend"
    IMAGE_NAME               = "printer3d-backend"
    SPRING_DATASOURCE_URL    = "jdbc:postgresql://postgres-sv:5432/production"
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
      version     = var.version
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
          version     = var.version
          environment = var.environment
        }
      }

      spec {
        container {
          name  = "printer3d-backend"
          image = "${var.image_name.backend}:${var.version}"

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

# Frontend Resources
resource "kubernetes_service" "frontend" {
  metadata {
    name      = "printer3d-frontend-stage-svc"
    namespace = var.namespace
  }

  spec {
    selector = {
      app         = "printer3d-frontend"
      version     = var.version
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
    PRODUCTION        = "false"
    VERSION           = var.version
    MODULE_NAME       = "Printer3D-frontend"
    IMAGE_NAME        = "printer3d-frontend"
    APPLICATION_HOME  = "/dist/Printer3D-frontend"
    BACKEND_PROXY     = "http://printer3d-backend-stage-svc:9000/"
  }
}

resource "kubernetes_deployment" "frontend" {
  metadata {
    name      = "printer3d-frontend"
    namespace = var.namespace
    labels = {
      app         = "printer3d-frontend"
      version     = var.version
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
          version     = var.version
          environment = var.environment
        }
      }

      spec {
        container {
          name  = "printer3d-frontend"
          image = "${var.image_name.frontend}:${var.version}"

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
