package web

import sttp.model.StatusCode
import zio._

import java.net.URI
import java.net.URL
import java.time.Instant
import mongo.LogModel
import mongo.LogModelFilter
import mongo.MongoDao

trait Logic {
  def add(log: LogModel): ZIO[Any, Nothing, Unit]
  def get(filter: LogModelFilter): ZIO[Any, Nothing, List[LogModel]]
}

class LogicImpl(mongo: MongoDao) extends Logic {
  override def add(log: LogModel) =
    (for {
      _ <- mongo.insertOne(log)
      _ <- ZIO.logInfo("Successfully inserted new entry to database")
    } yield ()).catchAll(e => ZIO.log(e.getMessage))

  override def get(filter: LogModelFilter) =
    (for {
      found <- mongo.find(filter.bson.asDocument).map(_.toList)
      _ <- ZIO.logInfo("Successfully read an entry to database")
    } yield found).catchAll(e =>
      ZIO.log(e.getMessage) *> ZIO.succeed(List.empty)
    )
}

object Logic {
  val live = ZLayer.fromZIO(
    for {
      mongo <- ZIO.service[MongoDao]
      logic = new LogicImpl(mongo)
    } yield logic
  )
}
