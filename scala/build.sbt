scalaVersion := "3.2.2"

name := "log-collector"
version := "1.0"

val zioVersion = "2.0.2"
val zioConfigVersion = "3.0.2"
val zioLoggingVersion = "2.1.4"
val oolongVersion = "0.1"

libraryDependencies ++= Seq(
  "io.netty" % "netty-all" % "4.1.84.Final",
  "com.typesafe" % "config" % "1.4.2",
  "com.iheart" %% "ficus" % "1.5.2",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.1.2",
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-logging" % zioLoggingVersion,
  "dev.zio" %% "zio-logging-slf4j-bridge" % zioLoggingVersion,
  "dev.zio" %% "zio-config" % zioConfigVersion,
  "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
  "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-vertx-server-zio" % "1.1.3",
  "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.1.2",
  "com.softwaremill.sttp.tapir" %% "tapir-vertx-server" % "1.1.2",
  ("org.mongodb.scala" %% "mongo-scala-driver" % "4.7.0")
    .cross(CrossVersion.for3Use2_13),
  "ru.tinkoff" %% "oolong-bson" % oolongVersion,
  "ru.tinkoff" %% "oolong-core" % oolongVersion,
  "ru.tinkoff" %% "oolong-mongo" % oolongVersion
)
