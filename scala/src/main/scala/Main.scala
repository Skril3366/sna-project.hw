import zio.*
import zio.Console.*

object Main extends ZIOAppDefault{
  def run = for{
    _ <- printLine("Hello World")
  } yield ()
}
