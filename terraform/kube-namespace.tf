resource "kubernetes_namespace" "java-truck-graphql" {
  metadata {
    name = "java-truck-graphql"
  }
}