resource "kubernetes_deployment" "java-truck-graphql" {
  metadata {
    name = "java-truck-graphql"
    labels = {
      App = "java-truck-graphql"
    }
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }

  spec {
    replicas = 1
    selector {
      match_labels = {
        App = "java-truck-graphql"
      }
    }
    template {
      metadata {
        labels = {
          App = "java-truck-graphql"
        }
      }

      spec {

        image_pull_secrets {
          name = "regcred"
        }

        container {
          image = "${var.github_docker_registry_url}:${var.docker_image_tag}"
          name  = "java-truck-graphql"

          port {
            container_port = 8080
          }

          env_from {
            secret_ref {
              name = "java-truck-graphql"
            }
          }

          resources {
            limits {
              cpu    = "2.0"
              memory = "4096Mi"
            }
            requests {
              cpu    = "250m"
              memory = "50Mi"
            }
          }
        }
      }
    }
  }
}