# First, import the namespace if it already exists
terraform import kubernetes_namespace.develop develop

# Import frontend service
terraform import module.frontend.kubernetes_service.frontend develop/printer3d-frontend-dev-svc

# Import frontend configmap
terraform import module.frontend.kubernetes_config_map.frontend develop/printer3d-frontend-environment

# Import frontend deployment
terraform import module.frontend.kubernetes_deployment.frontend develop/printer3d-frontend

# Refresh the state to ensure it matches the current kubernetes state
terraform refresh -var-file="local.tfvars"

# Plan to see if there are any differences between the state and the configuration
terraform plan -var-file="local.tfvars"
