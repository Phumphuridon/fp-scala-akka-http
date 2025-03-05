package routes

import types.Course
import types.CourseJsonProtocol._ 

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import akka.http.scaladsl.model.StatusCodes
import java.io.ObjectInputFilter.Status

import types.{Course, PartialCourse, Courses}
import services.CourseService


class CourseRoutes(courseService: CourseService) {
    val createCourse: Route = post {
        path("createCourse"){
            entity(as[Course]){ 
                course => val checkIfExist = courseService.getCourse(course.courseId)
                checkIfExist match {
                    case Some(value) => complete(StatusCodes.Conflict, "Course already exist")
                    case None => 
                        val result = courseService.createCourse(course)
                        complete(StatusCodes.Created, result)
                }
            }
        }
    }

    val getCourses: Route = get {
        path("getAllCourses"){
            val result = courseService.getCourses()
            complete(StatusCodes.OK, result)
        }
    }

    val getCourse: Route = get {
        path("getCourse" / IntNumber){ courseId =>
            val result = courseService.getCourse(courseId)
            result match {
                case Some(course) => complete(StatusCodes.OK, course)
                case None => complete(StatusCodes.NotFound, "Course not found")
            }
        }
    }

    val updateCourse: Route = put {
        path("updateCourse" / IntNumber){ courseId =>
            entity(as[PartialCourse]){ course =>
                val result = courseService.updateCourse(courseId, course)
                result match {
                    case Some(value) => complete(StatusCodes.OK, value)
                    case None => complete(StatusCodes.NotFound, "Course not found")
                }
            }
        }
    }

    val deleteCourse: Route = delete {
        path("deleteCourse" / IntNumber){ courseId =>
            val result = courseService.deleteCourse(courseId)
            result match {
                case Some(value) => complete(StatusCodes.OK, value)
                case None => complete(StatusCodes.NotFound, "Course not found")
            }
        }
    }

    val route: Route = concat(createCourse, getCourses, getCourse, updateCourse, deleteCourse);
}