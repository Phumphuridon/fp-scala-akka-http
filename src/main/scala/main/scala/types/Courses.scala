package types

import spray.json.DefaultJsonProtocol._
import spray.json._

final case class Courses(courses: Seq[Course])

object Courses {
    implicit  val format: RootJsonFormat[Courses] = jsonFormat1(Courses.apply)
}