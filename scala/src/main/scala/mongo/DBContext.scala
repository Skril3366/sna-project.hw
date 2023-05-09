package mongo
import config.Config
import org.mongodb.scala.Document
import org.mongodb.scala.MongoCollection
import org.mongodb.scala.MongoClient
import zio._

import java.util.concurrent.TimeUnit._
import scala.concurrent.duration.Duration

trait DBContext {
  val collection: Task[MongoCollection[Document]]
}

class CollectionImpl(config: Config) extends DBContext {
  val conf = config.mongo
  override val collection = (for {
    _ <- ZIO.log(f"Trying to connect to ${conf.connectionUri}")
    collection <- ZIO.attempt(
      MongoClient(conf.connectionUri)
        .getDatabase(conf.database)
        .getCollection(conf.collection)
    )
    _ <- ZIO.logInfo("Successfully connected to database")
  } yield collection).tapError(e =>
    ZIO.logError(f"Error while connecting to database: ${e.getMessage}")
  )
}

object DBContext {
  val live = ZLayer.fromZIO(
    for {
      conf <- ZIO.service[Config]
    } yield CollectionImpl(conf)
  )
}
