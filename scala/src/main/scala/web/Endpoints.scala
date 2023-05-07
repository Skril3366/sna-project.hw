package web

import config.Config
import mongo.LogModel
import mongo.LogModelFilter

import io.circe.generic.auto._
import sttp.model.StatusCode
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import sttp.tapir.ztapir._
import zio._

trait Endpoints {
  def add: Endpoint[Unit, LogModel, Unit, Unit, Any]
  def get: Endpoint[Unit, LogModelFilter, Unit, List[LogModel], Any]
}

class AppEndpoints(config: Config) extends Endpoints {
  override def add =
    endpoint.post
      .in("add")
      .in(jsonBody[LogModel])
  override def get =
    endpoint.post
      .in("get")
      .in(jsonBody[LogModelFilter])
      .out(jsonBody[List[LogModel]])
}

object Endpoints {
  val live = ZLayer.fromZIO(
    for {
      conf <- ZIO.service[Config]
      endpoints = new AppEndpoints(conf)
    } yield endpoints
  )
}
