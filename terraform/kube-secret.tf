resource "kubernetes_secret" "java-truck-graphql" {
  metadata {
    name = "java-truck-graphql"
    namespace = kubernetes_namespace.java-truck-graphql.metadata[0].name
  }

  data = {
    SPRING_DATASOURCE_URL = "jdbc:mysql://${aws_db_instance.java-truck-graphql.endpoint}:3306/graphql?serverTimeZone=UTC"
    SPRING_DATASOURCE_USERNAME = var.mysql_username
    SPRING_DATASOURCE_PASSWORD = var.mysql_password
  }
}
