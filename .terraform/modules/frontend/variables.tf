variable "namespace" {
  description = "Kubernetes namespace for frontend resources"
  type        = string
  default     = "develop"
}

variable "environment" {
  description = "Environment name (production, stage, etc)"
  type        = string
  default     = "dev"
}

variable "port" {
  description = "Frontend container and service port"
  type        = number
  default     = 9000
}

variable "artifactVersion" {
  description = "Frontend application version tag"
  type        = string
  default     = "latest"
}

variable "image_name" {
  description = "Frontend image name without tag"
  type        = string
  default     = "adamantinoo/printer3d-frontend"
} 
