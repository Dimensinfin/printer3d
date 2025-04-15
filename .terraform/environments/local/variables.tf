variable "namespace" {
  description = "Kubernetes namespace for all resources"
  type        = string
  default     = "local"
}

variable "port" {
  description = "Container and service port"
  type        = number
  default     = 9000
}

variable "artifactVersion" {
  description = "Frontend version"
  type        = string
  default     = "latest"
}

variable "backend_version" {
  description = "Backend version"
  type        = string
  default     = "latest"
}

variable "frontend_image_name" {
  description = "Frontend image name"
  type        = string
  default     = "adamantinoo/printer3d-frontend"
}

variable "backend_image_name" {
  description = "Backend image name"
  type        = string
  default     = "adamantinoo/printer3d-backend"
} 
