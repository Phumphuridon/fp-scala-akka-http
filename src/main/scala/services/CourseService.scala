package services

import akka.actor.typed.ActorRef
import akka.actor.typed.scaladsl.AskPattern._
import actors.CourseActor
import scala.concurrent.ExecutionContext
import akka.util.Timeout
import scala.concurrent.duration.DurationInt
import types.{Course, Courses, PartialCourse}
import scala.concurrent.Future
import akka.actor.typed.ActorSystem
import actors.CourseActor
import types.{Courses, PartialCourse, Course}

class CourseService(courseActor: ActorRef[CourseActor.Command])(implicit ex: ExecutionContext, system: ActorSystem[_]){

    implicit val timeout: Timeout = 3.seconds

    def createCourse(course: Course): Future[Option[Course]] =
        courseActor.ask(CourseActor.CreateCourse(course, _))
    
    def getAllCourses(): Future[Courses] =
        courseActor.ask(CourseActor.GetAllCourses(_))

    def getCourse(courseId: Int): Future[Option[Course]] =
        courseActor.ask(CourseActor.GetCourse(courseId, _))

    def updateCourse(courseId: Int, partialCourse: PartialCourse): Future[Option[Course]] =
        courseActor.ask(CourseActor.UpdateCourse(courseId, partialCourse, _))

    def deleteCourse(courseId: Int): Future[Option[Course]] =
        courseActor.ask(CourseActor.DeleteCourse(courseId, _))
}