name := """twitter_analysis"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.twitter4j" % "twitter4j-stream" % "3.0.3",
  "mysql" % "mysql-connector-java" % "5.1.12",
  "org.apache.rat" % "apache-rat" % "0.6",
  "com.rabbitmq" % "amqp-client" % "3.3.5"
)



