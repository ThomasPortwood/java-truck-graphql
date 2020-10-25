resource "kubernetes_secret" "java-truck-graphql" {
  metadata {
    name = "graphql"
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }

  data = {
    SPRING_DATASOURCE_URL = var.spring_datasource_url
    SPRING_DATASOURCE_USERNAME = var.spring_datasource_username
    SPRING_DATASOURCE_PASSWORD = var.spring_datasource_password
  }
}
