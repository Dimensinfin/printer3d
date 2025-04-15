variable "namespace" {
  description = "Kubernetes namespace for all resources"
  type        = string
  default     = "production"
}

variable "environment" {
  description = "Environment name (production, stage, etc)"
  type        = string
  default     = "production"
}

variable "port" {
  description = "Container and service port"
  type        = number
  default     = 9000
}

variable "version" {
  description = "Application version tag"
  type        = string
  default     = "0.20.0-2"
}

variable "image_name" {
  description = "Base image name without tag"
  type = object({
    frontend = string
    backend  = string
  })
  default = {
    frontend = "adamantinoo/printer3d-frontend"
    backend  = "adamantinoo/printer3d-backend"
  }
} 
