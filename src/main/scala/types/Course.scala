package types

import spray.json.DefaultJsonProtocol._
import spray.json._

final case class Course(
    courseId: Int, 
    courseName: String, 
    courseDescription: String, 
    instructors: Seq[String],
    courseCredit: Int
)

object Course {
    implicit val format: RootJsonFormat[Course] = jsonFormat5(Course.apply)
}