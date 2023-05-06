package mongo
import config.Config
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.MongoClient
import zio._

import java.util.concurrent.TimeUnit._
import scala.concurrent.duration.Duration

trait DBContext {
  val collection: Task[MongoCollection[BsonDocument]]
}

class CollectionImpl(config: Config) extends DBContext {
  val conf = config.mongo
  override val collection = ZIO.attempt {
    MongoClient(conf.connectionUri)
      .getDatabase(conf.database)
      .getCollection(conf.collection)
  }
}

object DBContext {
  val live = ZLayer.fromZIO(
    for {
      conf <- ZIO.service[Config]
      _ <- ZIO.logInfo("Connecting to database...")
    } yield CollectionImpl(conf)
  )
}
