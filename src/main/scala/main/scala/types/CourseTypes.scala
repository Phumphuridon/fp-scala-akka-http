package types

import spray.json.{RootJsonFormat, DefaultJsonProtocol}

final case class Course(
    courseId: Int, 
    courseName: String, 
    courseDescription: String, 
    instructor: List[String], 
    courseCredit: Int
)

final case class Courses(courses: List[Course])
    
final case class PartialCourse(
    courseName: Option[String] = None,
    courseDescription: Option[String] = None,
    instructor: Option[List[String]] = None,
    courseCredit: Option[Int] = None
)

object CourseJsonProtocol extends DefaultJsonProtocol {
    implicit val courseJsonFormat: RootJsonFormat[Course] = jsonFormat5(Course.apply)
    implicit val partialCourseJsonFormat: RootJsonFormat[PartialCourse] = jsonFormat4(PartialCourse.apply)
    implicit val coursesJsonFormat: RootJsonFormat[Courses] = jsonFormat1(Courses.apply)
}