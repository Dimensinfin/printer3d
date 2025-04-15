variable "environment" {
  description = "Environment name (production, stage, etc)"
  type        = string
  default     = "production"
}

variable "port" {
  description = "Backend container and service port"
  type        = number
  default     = 9000
}

variable "artifactVersion" {
  description = "Backend application version tag"
  type        = string
  default     = "0.20.0-2"
}

variable "image_name" {
  description = "Backend image name without tag"
  type        = string
  default     = "adamantinoo/printer3d-backend"
} 
