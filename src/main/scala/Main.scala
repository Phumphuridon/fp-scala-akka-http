import actors.CourseActor
import routes.CourseRoute
import services.CourseService
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import scala.concurrent.ExecutionContext

@main def main(): Unit =
    implicit val system: ActorSystem[Nothing] = ActorSystem(CourseActor(), "CourseActor")
    implicit val ec: ExecutionContext = system.executionContext

    val courseActor = system.systemActorOf(CourseActor(), "CourseActor")
    val courseService = new CourseService(courseActor)
    val courseRoute = new CourseRoute(courseService)

    Http().newServerAt("localhost", 8000).bind(courseRoute.route)
    println("Server started at http://localhost:8000/")