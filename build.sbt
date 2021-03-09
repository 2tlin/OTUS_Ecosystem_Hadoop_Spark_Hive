name := "OTUS_Ecosystem_Hadoop_Spark_Hive-2"

version := "0.1"

scalaVersion := "2.12.13"

lazy val circeVersion = "0.13.0"

libraryDependencies += "io.circe" %% "circe-parser" % circeVersion
libraryDependencies += "io.circe" %% "circe-core" % circeVersion
libraryDependencies += "io.circe" %% "circe-generic" % circeVersion
