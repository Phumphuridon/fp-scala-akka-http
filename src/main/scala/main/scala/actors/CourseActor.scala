package actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorRef
import types.Course
import types.Courses
import types.PartialCourse

object CourseActor {
    sealed trait Command
    final case class CreateCourse(course: Course, replyTo: ActorRef[Option[Course]]) extends Command
    final case class GetAllCourses(replyTo: ActorRef[Courses]) extends Command
    final case class GetCourse(courseId: Int, replyTo: ActorRef[Option[Course]]) extends Command
    final case class UpdateCourse(courseId: Int, partialCourse: PartialCourse, replyTo: ActorRef[Option[Course]]) extends Command
    final case class DeleteCourse(courseId: Int, replyTo: ActorRef[Option[Course]]) extends Command

    def apply(): Behavior[Command] = InMemoryRegistry(Set.empty)

    private def InMemoryRegistry(courses: Set[Course]): Behavior[Command] =
        Behaviors.receiveMessage {
            case CreateCourse(course, replyTo) => 
                if (courses.exists(_.courseId == course.courseId)) {
                    replyTo ! None
                    Behaviors.same
                } else {
                    replyTo ! Some(course)
                    InMemoryRegistry(courses + course)
                }
            case GetAllCourses(replyTo) =>
                replyTo ! Courses(courses.toSeq)
                Behaviors.same
            case GetCourse(courseId, replyTo) =>
                replyTo ! courses.find(_.courseId == courseId)
                Behaviors.same
            case UpdateCourse(courseId, partialCourse, replyTo) =>
                courses.find(_.courseId == courseId) match {
                    case Some(existingCourse) =>
                        val updatedCourse = existingCourse.copy(
                            courseName = partialCourse.courseName.getOrElse(existingCourse.courseName),
                            courseDescription = partialCourse.courseDescription.getOrElse(existingCourse.courseDescription),
                            instructors = partialCourse.instructors.getOrElse(existingCourse.instructors),
                            courseCredit = partialCourse.courseCredit.getOrElse(existingCourse.courseCredit)
                        )

                        replyTo ! Some(updatedCourse)
                        InMemoryRegistry(courses - existingCourse + updatedCourse)

                    case None =>
                        replyTo ! None
                        Behaviors.same
                }
            case DeleteCourse(courseId, replyTo) =>
                courses.find(_.courseId == courseId) match {
                    case Some(deletingCourse) =>
                        replyTo ! Some(deletingCourse)
                        InMemoryRegistry(courses - deletingCourse)
                    case None => 
                        replyTo ! None
                        Behaviors.same
                }
        }
}