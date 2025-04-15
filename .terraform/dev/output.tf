resource "kubernetes_manifest" "pod_develop_postgres_8867c8cff_4xms9" {
  manifest = {
    "apiVersion" = "v1"
    "kind" = "Pod"
    "metadata" = {
      "creationTimestamp" = "2025-04-13T15:12:00Z"
      "generateName" = "postgres-8867c8cff-"
      "labels" = {
        "app" = "postgres"
        "pod-template-hash" = "8867c8cff"
      }
      "name" = "postgres-8867c8cff-4xms9"
      "namespace" = "develop"
      "ownerReferences" = [
        {
          "apiVersion" = "apps/v1"
          "blockOwnerDeletion" = true
          "controller" = true
          "kind" = "ReplicaSet"
          "name" = "postgres-8867c8cff"
          "uid" = "7d2a9c7a-34d8-4586-8309-c0d7fc1bb415"
        },
      ]
      "resourceVersion" = "967"
      "uid" = "710cb60b-eb86-4188-a5e0-aa3dfd237f39"
    }
    "spec" = {
      "containers" = [
        {
          "envFrom" = [
            {
              "configMapRef" = {
                "name" = "postgres-secret"
              }
            },
          ]
          "image" = "postgres:14"
          "imagePullPolicy" = "IfNotPresent"
          "name" = "postgres"
          "ports" = [
            {
              "containerPort" = 5432
              "protocol" = "TCP"
            },
          ]
          "resources" = {}
          "terminationMessagePath" = "/dev/termination-log"
          "terminationMessagePolicy" = "File"
          "volumeMounts" = [
            {
              "mountPath" = "/var/lib/postgresql/data"
              "name" = "postgresdata"
            },
            {
              "mountPath" = "/var/run/secrets/kubernetes.io/serviceaccount"
              "name" = "kube-api-access-tdjz2"
              "readOnly" = true
            },
          ]
        },
      ]
      "dnsPolicy" = "ClusterFirst"
      "enableServiceLinks" = true
      "nodeName" = "minikube"
      "preemptionPolicy" = "PreemptLowerPriority"
      "priority" = 0
      "restartPolicy" = "Always"
      "schedulerName" = "default-scheduler"
      "securityContext" = {}
      "serviceAccount" = "default"
      "serviceAccountName" = "default"
      "terminationGracePeriodSeconds" = 30
      "tolerations" = [
        {
          "effect" = "NoExecute"
          "key" = "node.kubernetes.io/not-ready"
          "operator" = "Exists"
          "tolerationSeconds" = 300
        },
        {
          "effect" = "NoExecute"
          "key" = "node.kubernetes.io/unreachable"
          "operator" = "Exists"
          "tolerationSeconds" = 300
        },
      ]
      "volumes" = [
        {
          "name" = "postgresdata"
          "persistentVolumeClaim" = {
            "claimName" = "postgres-volume-claim-develop"
          }
        },
        {
          "name" = "kube-api-access-tdjz2"
          "projected" = {
            "defaultMode" = 420
            "sources" = [
              {
                "serviceAccountToken" = {
                  "expirationSeconds" = 3607
                  "path" = "token"
                }
              },
              {
                "configMap" = {
                  "items" = [
                    {
                      "key" = "ca.crt"
                      "path" = "ca.crt"
                    },
                  ]
                  "name" = "kube-root-ca.crt"
                }
              },
              {
                "downwardAPI" = {
                  "items" = [
                    {
                      "fieldRef" = {
                        "apiVersion" = "v1"
                        "fieldPath" = "metadata.namespace"
                      }
                      "path" = "namespace"
                    },
                  ]
                }
              },
            ]
          }
        },
      ]
    }
    "status" = {
      "conditions" = [
        {
          "lastProbeTime" = null
          "lastTransitionTime" = "2025-04-13T15:12:25Z"
          "status" = "True"
          "type" = "PodReadyToStartContainers"
        },
        {
          "lastProbeTime" = null
          "lastTransitionTime" = "2025-04-13T15:12:00Z"
          "status" = "True"
          "type" = "Initialized"
        },
        {
          "lastProbeTime" = null
          "lastTransitionTime" = "2025-04-13T15:12:25Z"
          "status" = "True"
          "type" = "Ready"
        },
        {
          "lastProbeTime" = null
          "lastTransitionTime" = "2025-04-13T15:12:25Z"
          "status" = "True"
          "type" = "ContainersReady"
        },
        {
          "lastProbeTime" = null
          "lastTransitionTime" = "2025-04-13T15:12:00Z"
          "status" = "True"
          "type" = "PodScheduled"
        },
      ]
      "containerStatuses" = [
        {
          "containerID" = "docker://eae44efe6893d3bea3015f40ce83b200802582d65aa8c8b100216cfe12889721"
          "image" = "postgres:14"
          "imageID" = "docker-pullable://postgres@sha256:1e6c52c366e39e869184256c45757e1c85ba15b3d244b0a2cea640da6df1c4e3"
          "lastState" = {}
          "name" = "postgres"
          "ready" = true
          "restartCount" = 0
          "started" = true
          "state" = {
            "running" = {
              "startedAt" = "2025-04-13T15:12:25Z"
            }
          }
          "volumeMounts" = [
            {
              "mountPath" = "/var/lib/postgresql/data"
              "name" = "postgresdata"
            },
            {
              "mountPath" = "/var/run/secrets/kubernetes.io/serviceaccount"
              "name" = "kube-api-access-tdjz2"
              "readOnly" = true
              "recursiveReadOnly" = "Disabled"
            },
          ]
        },
      ]
      "hostIP" = "192.168.49.2"
      "hostIPs" = [
        {
          "ip" = "192.168.49.2"
        },
      ]
      "phase" = "Running"
      "podIP" = "10.244.0.7"
      "podIPs" = [
        {
          "ip" = "10.244.0.7"
        },
      ]
      "qosClass" = "BestEffort"
      "startTime" = "2025-04-13T15:12:00Z"
    }
  }
}

resource "kubernetes_manifest" "service_develop_postgres_sv" {
  manifest = {
    "apiVersion" = "v1"
    "kind" = "Service"
    "metadata" = {
      "annotations" = {
        "kubectl.kubernetes.io/last-applied-configuration" = <<-EOT
        {"apiVersion":"v1","kind":"Service","metadata":{"annotations":{},"labels":{"app":"postgres"},"name":"postgres-sv","namespace":"develop"},"spec":{"ports":[{"nodePort":30203,"port":5432,"protocol":"TCP","targetPort":5432}],"selector":{"app":"postgres"},"type":"NodePort"}}
        
        EOT
      }
      "creationTimestamp" = "2025-04-13T15:11:51Z"
      "labels" = {
        "app" = "postgres"
      }
      "name" = "postgres-sv"
      "namespace" = "develop"
      "resourceVersion" = "917"
      "uid" = "d6f9907b-9db1-435e-b7da-1d686291d32b"
    }
    "spec" = {
      "clusterIP" = "10.99.144.30"
      "clusterIPs" = [
        "10.99.144.30",
      ]
      "externalTrafficPolicy" = "Cluster"
      "internalTrafficPolicy" = "Cluster"
      "ipFamilies" = [
        "IPv4",
      ]
      "ipFamilyPolicy" = "SingleStack"
      "ports" = [
        {
          "nodePort" = 30203
          "port" = 5432
          "protocol" = "TCP"
          "targetPort" = 5432
        },
      ]
      "selector" = {
        "app" = "postgres"
      }
      "sessionAffinity" = "None"
      "type" = "NodePort"
    }
    "status" = {
      "loadBalancer" = {}
    }
  }
}

resource "kubernetes_manifest" "deployment_develop_postgres" {
  manifest = {
    "apiVersion" = "apps/v1"
    "kind" = "Deployment"
    "metadata" = {
      "annotations" = {
        "deployment.kubernetes.io/revision" = "1"
        "kubectl.kubernetes.io/last-applied-configuration" = <<-EOT
        {"apiVersion":"apps/v1","kind":"Deployment","metadata":{"annotations":{},"name":"postgres","namespace":"develop"},"spec":{"replicas":1,"selector":{"matchLabels":{"app":"postgres"}},"template":{"metadata":{"labels":{"app":"postgres"}},"spec":{"containers":[{"envFrom":[{"configMapRef":{"name":"postgres-secret"}}],"image":"postgres:14","imagePullPolicy":"IfNotPresent","name":"postgres","ports":[{"containerPort":5432}],"volumeMounts":[{"mountPath":"/var/lib/postgresql/data","name":"postgresdata"}]}],"volumes":[{"name":"postgresdata","persistentVolumeClaim":{"claimName":"postgres-volume-claim-develop"}}]}}}}
        
        EOT
      }
      "creationTimestamp" = "2025-04-13T15:12:00Z"
      "generation" = 1
      "name" = "postgres"
      "namespace" = "develop"
      "resourceVersion" = "971"
      "uid" = "c495bd8a-72ce-4fde-91fe-2a0cead3f595"
    }
    "spec" = {
      "progressDeadlineSeconds" = 600
      "replicas" = 1
      "revisionHistoryLimit" = 10
      "selector" = {
        "matchLabels" = {
          "app" = "postgres"
        }
      }
      "strategy" = {
        "rollingUpdate" = {
          "maxSurge" = "25%"
          "maxUnavailable" = "25%"
        }
        "type" = "RollingUpdate"
      }
      "template" = {
        "metadata" = {
          "creationTimestamp" = null
          "labels" = {
            "app" = "postgres"
          }
        }
        "spec" = {
          "containers" = [
            {
              "envFrom" = [
                {
                  "configMapRef" = {
                    "name" = "postgres-secret"
                  }
                },
              ]
              "image" = "postgres:14"
              "imagePullPolicy" = "IfNotPresent"
              "name" = "postgres"
              "ports" = [
                {
                  "containerPort" = 5432
                  "protocol" = "TCP"
                },
              ]
              "resources" = {}
              "terminationMessagePath" = "/dev/termination-log"
              "terminationMessagePolicy" = "File"
              "volumeMounts" = [
                {
                  "mountPath" = "/var/lib/postgresql/data"
                  "name" = "postgresdata"
                },
              ]
            },
          ]
          "dnsPolicy" = "ClusterFirst"
          "restartPolicy" = "Always"
          "schedulerName" = "default-scheduler"
          "securityContext" = {}
          "terminationGracePeriodSeconds" = 30
          "volumes" = [
            {
              "name" = "postgresdata"
              "persistentVolumeClaim" = {
                "claimName" = "postgres-volume-claim-develop"
              }
            },
          ]
        }
      }
    }
    "status" = {
      "availableReplicas" = 1
      "conditions" = [
        {
          "lastTransitionTime" = "2025-04-13T15:12:25Z"
          "lastUpdateTime" = "2025-04-13T15:12:25Z"
          "message" = "Deployment has minimum availability."
          "reason" = "MinimumReplicasAvailable"
          "status" = "True"
          "type" = "Available"
        },
        {
          "lastTransitionTime" = "2025-04-13T15:12:00Z"
          "lastUpdateTime" = "2025-04-13T15:12:25Z"
          "message" = "ReplicaSet \"postgres-8867c8cff\" has successfully progressed."
          "reason" = "NewReplicaSetAvailable"
          "status" = "True"
          "type" = "Progressing"
        },
      ]
      "observedGeneration" = 1
      "readyReplicas" = 1
      "replicas" = 1
      "updatedReplicas" = 1
    }
  }
}

resource "kubernetes_manifest" "replicaset_develop_postgres_8867c8cff" {
  manifest = {
    "apiVersion" = "apps/v1"
    "kind" = "ReplicaSet"
    "metadata" = {
      "annotations" = {
        "deployment.kubernetes.io/desired-replicas" = "1"
        "deployment.kubernetes.io/max-replicas" = "2"
        "deployment.kubernetes.io/revision" = "1"
      }
      "creationTimestamp" = "2025-04-13T15:12:00Z"
      "generation" = 1
      "labels" = {
        "app" = "postgres"
        "pod-template-hash" = "8867c8cff"
      }
      "name" = "postgres-8867c8cff"
      "namespace" = "develop"
      "ownerReferences" = [
        {
          "apiVersion" = "apps/v1"
          "blockOwnerDeletion" = true
          "controller" = true
          "kind" = "Deployment"
          "name" = "postgres"
          "uid" = "c495bd8a-72ce-4fde-91fe-2a0cead3f595"
        },
      ]
      "resourceVersion" = "970"
      "uid" = "7d2a9c7a-34d8-4586-8309-c0d7fc1bb415"
    }
    "spec" = {
      "replicas" = 1
      "selector" = {
        "matchLabels" = {
          "app" = "postgres"
          "pod-template-hash" = "8867c8cff"
        }
      }
      "template" = {
        "metadata" = {
          "creationTimestamp" = null
          "labels" = {
            "app" = "postgres"
            "pod-template-hash" = "8867c8cff"
          }
        }
        "spec" = {
          "containers" = [
            {
              "envFrom" = [
                {
                  "configMapRef" = {
                    "name" = "postgres-secret"
                  }
                },
              ]
              "image" = "postgres:14"
              "imagePullPolicy" = "IfNotPresent"
              "name" = "postgres"
              "ports" = [
                {
                  "containerPort" = 5432
                  "protocol" = "TCP"
                },
              ]
              "resources" = {}
              "terminationMessagePath" = "/dev/termination-log"
              "terminationMessagePolicy" = "File"
              "volumeMounts" = [
                {
                  "mountPath" = "/var/lib/postgresql/data"
                  "name" = "postgresdata"
                },
              ]
            },
          ]
          "dnsPolicy" = "ClusterFirst"
          "restartPolicy" = "Always"
          "schedulerName" = "default-scheduler"
          "securityContext" = {}
          "terminationGracePeriodSeconds" = 30
          "volumes" = [
            {
              "name" = "postgresdata"
              "persistentVolumeClaim" = {
                "claimName" = "postgres-volume-claim-develop"
              }
            },
          ]
        }
      }
    }
    "status" = {
      "availableReplicas" = 1
      "fullyLabeledReplicas" = 1
      "observedGeneration" = 1
      "readyReplicas" = 1
      "replicas" = 1
    }
  }
}
