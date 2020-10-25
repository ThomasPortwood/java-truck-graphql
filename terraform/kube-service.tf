resource "kubernetes_service" "java-truck-graphql" {
  metadata {
    name      = "java-truck-graphql"
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }
  spec {
    selector = {
      App = kubernetes_deployment.java-truck-graphql.spec.0.template.0.metadata[0].labels.App
    }
    port {
      port = 8080
    }
    type = "ClusterIP"
  }
}