resource "kubernetes_secret" "java-truck-graphql" {
  metadata {
    name = "java-truck-graphql"
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }

  data = {
    SPRING_DATASOURCE_URL = var.spring_datasource_url
    SPRING_DATASOURCE_USERNAME = var.mysql_username
    SPRING_DATASOURCE_PASSWORD = var.mysql_password
  }
}
