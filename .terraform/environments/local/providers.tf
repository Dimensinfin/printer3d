terraform {
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.36.0"
    }
  }
}

provider "kubernetes" {
  host = "https://kubernetes.docker.internal:6443"  # Minikube endpoint
  config_path    = "~/.kube/config"
  config_context = "minikube"

  # Optional: If you need to skip TLS verification (not recommended for production)
  # insecure = true
}

# If you're using minikube, you might need this configuration instead:
# provider "kubernetes" {
#   host = "https://kubernetes.docker.internal:6443"  # Your cluster endpoint
#   config_path    = "~/.kube/config"
#   config_context = "minikube"
# } 
