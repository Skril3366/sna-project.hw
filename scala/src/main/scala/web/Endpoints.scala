package web

import config.Config
import mongo.LogModel

import io.circe.generic.auto._
import sttp.model.StatusCode
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.ztapir._
import zio._

trait Endpoints {
  def add: Endpoint[Unit, LogModel, Unit, Unit, Any]
}

class AppEndpoints(config: Config) extends Endpoints {
  override def add = 
    endpoint.post
      .in("add")
      .in(jsonBody[LogModel])
}

object Endpoints {
  val live = ZLayer.fromZIO(
    for {
      conf <- ZIO.service[Config]
      endpoints = new AppEndpoints(conf)
    } yield endpoints
  )
}
