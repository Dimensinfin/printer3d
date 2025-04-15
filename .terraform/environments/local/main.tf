module "frontend" {
  source = "../../modules/frontend"

  namespace       = var.namespace
  environment     = "local"
  port            = var.port
  artifactVersion = var.artifactVersion
  image_name      = var.frontend_image_name
}

module "backend" {
  source = "../../modules/backend"

  namespace       = var.namespace
  environment     = "local"
  port            = var.port
  artifactVersion = var.backend_version
  image_name      = var.backend_image_name
} 
