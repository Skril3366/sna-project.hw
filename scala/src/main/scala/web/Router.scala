package web
import io.vertx.ext.web.{Router => VRouter, _}
import sttp.monad.MonadError
import sttp.tapir.Endpoint
import sttp.tapir.model.ServerRequest
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.interceptor.EndpointInterceptor
import sttp.tapir.server.interceptor.Interceptor
import sttp.tapir.server.interceptor.RequestHandler
import sttp.tapir.server.interceptor.RequestInterceptor
import sttp.tapir.server.interceptor.RequestResult
import sttp.tapir.server.interceptor.Responder
import sttp.tapir.server.interceptor.log.DefaultServerLog
import sttp.tapir.server.vertx.zio.VertxZioServerInterpreter
import sttp.tapir.server.vertx.zio.VertxZioServerOptions
import sttp.tapir.ztapir.*
import zio.*
import zio.logging.LogAnnotation
import zio.logging.LogFormat
import zio.logging.consoleJson

import java.util.UUID

trait Router {
  def route(r: VRouter): Task[Route]
}

class UrlShortenerRouter(
    logic: Logic,
    endpoints: Endpoints
) extends Router {

  private implicit val runtime: Runtime[Any] = zio.Runtime.default

  private type EndpointAndLogic[I, O] =
    (Endpoint[Unit, I, Unit, O, Any], I => ZIO[Any, Nothing, O])

  private def compose[I, O](x: EndpointAndLogic[I, O]) = x match {
    case (endpoint, logic) =>
      VertxZioServerInterpreter().route(endpoint.zServerLogic((logic(_))))
  }

  private val addEndpoint = compose(endpoints.add, logic.add)
  private val getEndpoint = compose(endpoints.get, logic.get)

  override def route(router: VRouter): Task[Route] = {
    List(addEndpoint, getEndpoint).foreach(_(router))
    ZIO.succeed(
      router.route()
    )
  }
}

object Router {
  val live = ZLayer.fromZIO(
    for {
      logic <- ZIO.service[Logic]
      endpoints <- ZIO.service[Endpoints]
      router = new UrlShortenerRouter(logic, endpoints)
    } yield router
  )
}
