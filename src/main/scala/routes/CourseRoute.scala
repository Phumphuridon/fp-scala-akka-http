package routes

import types.{Course, PartialCourse}
import services.CourseService

import scala.concurrent.ExecutionContext

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes

import scala.util.{Success, Failure}

class CourseRoute(courseService: CourseService)(implicit ec: ExecutionContext, system: ActorSystem[_]){
    val createCourse: Route = post {
        path("createCourse"){
            entity(as[Course]){ course => 
                onComplete(courseService.createCourse(course)) {
                    case Success(Some(createdCourse)) => complete(StatusCodes.Created, createdCourse)
                    case Success(None) => complete(StatusCodes.Conflict, "Course already exist.")
                    case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
                }
            }
        }
    }

    val getAllCourses: Route = get {
        path("getAllCourses"){
            complete(StatusCodes.OK, courseService.getAllCourses())
        }
    }

    val getCourse: Route = get {
        path("getCourse" / IntNumber){ courseId =>
            onComplete(courseService.getCourse(courseId)) {
                case Success(Some(course)) => complete(StatusCodes.OK, course)
                case Success(None) => complete(StatusCodes.NotFound, "Course not found.")
                case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
        }
    }

    val updateCourse: Route = put {
        path("updateCourse" / IntNumber){ courseId =>
            entity(as[PartialCourse]){ courseUpdate =>
                onComplete(courseService.updateCourse(courseId, courseUpdate)) {
                    case Success(Some(updatedCourse)) => complete(StatusCodes.OK, updatedCourse)
                    case Success(None) => complete(StatusCodes.NotFound, "Course not found.")
                    case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
                }
            }
        }
    }

    val deleteCourse: Route = delete {
        path("deleteCourse" / IntNumber) { courseId =>
            onComplete(courseService.deleteCourse(courseId)) {
                case Success(Some(deletedCourse)) => complete(StatusCodes.OK, deletedCourse)
                case Success(None) => complete(StatusCodes.NotFound, "Course not found.")
                case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
            }
        }
    }

    val route: Route = concat(createCourse, getAllCourses, getCourse, updateCourse, deleteCourse)
}