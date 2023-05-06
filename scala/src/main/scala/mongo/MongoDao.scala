package mongo

import config.Config

import java.io.File
import scala.jdk.CollectionConverters.*

import org.mongodb.scala.*
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.bson.conversions.Bson
import zio.*

import ru.tinkoff.oolong.bson.*
import ru.tinkoff.oolong.bson.given
import ru.tinkoff.oolong.dsl.*
import ru.tinkoff.oolong.mongo.*

trait MongoDao {
  def find(query: Bson): ZIO[Any, Throwable, Seq[LogModel]]
  def insertOne(log: LogModel): ZIO[Any, Throwable, Unit]
}

case class MongoDaoImpl(collection: MongoCollection[BsonDocument])
    extends MongoDao {
  override def insertOne(log: LogModel) =
    ZIO
      .fromFuture(_ => collection.insertOne(log.bson.asDocument()).toFuture())
      .unit

  override def find(query: Bson) =
    ZIO
      .fromFuture(_ =>
        collection
          .find(query)
          .toFuture()
      )
      .flatMap(docs =>
        ZIO.foreach(docs) { doc =>
          ZIO.fromTry(BsonDecoder[LogModel].fromBson(doc))
        }
      )
}

object MongoDao {
  val live =
    ZLayer {
      for {
        context <- ZIO.service[DBContext]
        collection <- context.collection
      } yield MongoDaoImpl(collection)
    }
}
