package web
import config.Config
import io.vertx.core.Vertx
import io.vertx.ext.web.{Router => VRouter, _}
import sttp.tapir.server.vertx.zio.VertxZioServerInterpreter._
import zio._
import zio._
import zio.logging.LogAnnotation

import java.util.UUID

trait Server {
  def run(): ZIO[Any, Throwable, Nothing]
}

class UrlShortenerServer(r: Router, conf: Config) extends Server {
  override def run(): ZIO[Any, Throwable, Nothing] = {
    ZIO.scoped(
      ZIO.acquireRelease(
        ZIO
          .attempt {
            val vertx = Vertx.vertx()
            val server = vertx.createHttpServer()
            val router = VRouter.router(vertx)
            r.route(router)
            server.requestHandler(router).listen(conf.server.port)
          }
          .flatMap(_.asRIO)
      ) { server =>
        ZIO.attempt(server.close()).flatMap(_.asRIO).orDie
      } *> ZIO.never
    )
  }
}

object Server {
  val live = ZLayer.fromZIO(
    for {
      router <- ZIO.service[Router]
      conf <- ZIO.service[Config]
      _ <- ZIO.logInfo(s"Starting server on port ${conf.server.port}")
    } yield UrlShortenerServer(router, conf)
  )
}
