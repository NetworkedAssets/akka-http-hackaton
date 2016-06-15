name := "hackaton-akka"

version := "1.0"

scalaVersion := "2.11.8"

lazy val akkaHttpVersion = "2.4.7"

val backendDeps = Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaHttpVersion
)

libraryDependencies ++= backendDeps