resource "aws_db_subnet_group" "java-truck-graphql" {
  name       = "java-truck-graphql"
  subnet_ids = ["subnet-01e4a10fbaff0c2d9", "subnet-0c99ddfaf8f972f87"]
  tags = {
    Name = "java-truck-graphql db subnet group"
  }
}

resource "aws_db_instance" "java-truck-graphql" {
  allocated_storage           = 20
  allow_major_version_upgrade = true
  storage_type                = "gp2"
  engine                      = "mysql"
  engine_version              = "8.0.20"
  instance_class              = "db.t2.micro"
  name                        = "JavaTruckGraphql"
  username                    = var.mysql_username
  password                    = var.mysql_password
  parameter_group_name        = "default.mysql8.0"
  publicly_accessible         = "true"
  db_subnet_group_name        = aws_db_subnet_group.java-truck-graphql.name
  tags = {
    Name = "graphql"
  }
}