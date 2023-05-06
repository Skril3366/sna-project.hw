package config

import zio._
import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

import java.io.File

case class Config(server: ServerConfig, mongo: MongoConfig)

case class MongoConfig(
    connectionUri: String,
    database: String,
    collection: String,
)

case class ServerConfig(
    port: Int
)

object Config {
  val live: ZLayer[Any, ReadError[String], Config] = ZLayer {
    read { descriptor[Config].from(TypesafeConfigSource.fromResourcePath) }
  }
}
