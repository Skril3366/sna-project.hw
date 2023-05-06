package web

import sttp.model.StatusCode
import zio._

import java.net.URI
import java.net.URL
import java.time.Instant
import mongo.LogModel
import mongo.MongoDao


trait Logic {
  def add(log: LogModel): ZIO[Any, Nothing, Unit]
}

class LogicImpl(mongo: MongoDao) extends Logic {
  override def add(log: LogModel) =
    mongo.insertOne(log).catchAll(e => ZIO.log(e.getMessage))
}

object Logic {
  val live = ZLayer.fromZIO(
    for {
      mongo <- ZIO.service[MongoDao]
      logic = new LogicImpl(mongo)
    } yield logic
  )
}
