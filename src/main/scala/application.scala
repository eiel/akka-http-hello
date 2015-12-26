import scala.concurrent._
import ExecutionContext.Implicits.global

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._


object Application extends App {
  implicit val actorSystem = ActorSystem("application-system")
  implicit val actorMaterializer = ActorMaterializer()

  val port: Int = sys.env.getOrElse("PORT", "3000").toInt

  val route = {
    path("") {
      get {
        complete {
          "Hello, World!"
        }
      }
    }
  }

  val binding: Future[Http.ServerBinding] = Http().bindAndHandle(route,"0.0.0.0", port)

  binding onFailure {
    case e: Exception => println(e.getMessage)
  }
}
