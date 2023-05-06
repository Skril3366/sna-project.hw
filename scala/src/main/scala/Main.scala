import web.Server
import web.Router
import web.Endpoints
import web.Logic
import config.Config
import mongo.MongoDao
import mongo.DBContext

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import zio.LogAnnotation.apply
import zio._
import zio.logging.LogAnnotation
import zio.logging.LogColor.apply
import zio.logging.LogFormat
import zio.logging.LogFormat._
import zio.logging.consoleJson
import zio.logging.slf4j.bridge.Slf4jBridge

import java.util.UUID

object Main extends ZIOAppDefault {

  val deps = ZLayer.make[Server](
    Server.live,
    Config.live,
    Router.live,
    Endpoints.live,
    Logic.live,
    MongoDao.live,
    DBContext.live,
  )

  val server: ZIO[Server, Throwable, Unit] = for {
    server <- ZIO.service[Server]
    _ <- server.run()
  } yield ()

  override def run = for {
    _ <- server.provide(deps)
  } yield ()

}
