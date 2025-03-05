import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http

import routes.CourseRoutes
import services.CourseService

@main def main(): Unit = {
    // Create ActorSystem
    implicit val actorSystem = ActorSystem(Behaviors.empty, "Course-API")
    
    val courseService = new CourseService()
    val courseRoutes = new CourseRoutes(courseService)

    // Bind the route to the server
    Http().newServerAt("localhost", 8000).bind(courseRoutes.route)
}
