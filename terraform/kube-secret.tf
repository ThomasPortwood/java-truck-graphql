resource "kubernetes_secret" "java-truck-graphql" {
  metadata {
    name = "graphql"
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }

  data = {
    SPRING_DATASOURCE_URL = aws_db_instance.java-truck-graphql.endpoint
    SPRING_DATASOURCE_USERNAME = var.mysql_username
    SPRING_DATASOURCE_PASSWORD = var.mysql_password
  }
}
