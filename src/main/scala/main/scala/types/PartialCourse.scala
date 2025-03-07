package types

import spray.json.DefaultJsonProtocol._
import spray.json._

final case class PartialCourse(
    courseName: Option[String], 
    courseDescription: Option[String], 
    instructors: Option[Seq[String]],
    courseCredit: Option[Int]
)

object PartialCourse {
    implicit val format: RootJsonFormat[PartialCourse] = jsonFormat4(PartialCourse.apply)
}