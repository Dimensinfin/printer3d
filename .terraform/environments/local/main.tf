module "frontend" {
  source = "../../modules/frontend"

  depends_on = [kubernetes_namespace.develop]
  
  namespace = "develop"
  environment     = "dev"
  port            = var.port
  artifactVersion = var.artifactVersion
  image_name      = var.frontend_image_name
}

# module "backend" {
#   source = "../../modules/backend"

#   depends_on = [kubernetes_namespace.develop]
  
#   environment     = "dev"
#   port            = var.port
#   artifactVersion = var.backend_version
#   image_name      = var.backend_image_name
# } 
