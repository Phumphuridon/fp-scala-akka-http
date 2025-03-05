package repository

import types.{Course, PartialCourse, Courses}

object CourseRepository {
    var courses: List[Course] = List()

    def createCourse(course: Course): Course = {
        courses = courses :+ course
        course
    }

    def getCourses(): Courses = {
        Courses(courses)
    }

    def getCourse(courseId: Int): Option[Course] = {
        courses.find(_.courseId == courseId)
    }

    def update(courseId: Int, course: PartialCourse): Option[Course] = {
        courses.find(_.courseId == courseId) match {
            case Some(value) =>
                val updatedCourse = value.copy(
                    courseName = course.courseName.getOrElse(value.courseName),
                    courseDescription = course.courseDescription.getOrElse(value.courseDescription),
                    instructor = course.instructor.getOrElse(value.instructor),
                    courseCredit = course.courseCredit.getOrElse(value.courseCredit)
                )
                courses = courses.map(c => if (c.courseId == courseId) updatedCourse else c)
                Some(updatedCourse)
            case None => None
        }
    }

    def delete(courseId: Int): Option[Course] = {
        courses.find(_.courseId == courseId) match {
            case Some(value) =>
                courses = courses.filter(_.courseId != courseId)
                Some(value)
            case None => None
        }
    }
}