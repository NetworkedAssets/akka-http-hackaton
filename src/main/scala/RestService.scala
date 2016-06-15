import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
/**
  * Created by mtulaza on 06.06.2016.
  */
object RestService extends App {
  implicit val system = ActorSystem("rest-system")
  implicit val materializer = ActorMaterializer()

  val host = "0.0.0.0"
  val port = 8087

  val routes =
    path("event" / "hook") {
      entity(as[String]) {
        body => {
          post {
            complete {
              val datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
              val message= s"$datetime   Body message: $body, satellite hook worked well!"
              println(message)
              message
            }
          }
        }
      }
    } ~
      path("event") {
        entity(as[String]){
          body => {
            post {
              complete {
                val datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                val message = s"$datetime   Other event on http://$host:$port/event obtained.."
                println(message)
                message
              }
            }
          }
        }
      } ~
      path("hello") {
        get {
          complete {
            "hello, world!"
          }
        }
      }

  val bindingFuture = Http().bindAndHandle(routes, host, port)

  println(s"Application run on: http://$host:$port/")
  println("Press enter to exit..")
  Console.in.read.toChar

  import system.dispatcher
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.shutdown())
}
