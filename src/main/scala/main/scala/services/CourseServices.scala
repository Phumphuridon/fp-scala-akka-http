package services

import types.{Course, PartialCourse, Courses}
import repository.CourseRepository

class CourseService {
    def createCourse(course: Course): Course = {
        CourseRepository.createCourse(course)
    }

    def getCourses(): Courses = {
        CourseRepository.getCourses()
    }

    def getCourse(courseId: Int): Option[Course] = {
        CourseRepository.getCourse(courseId)
    }

    def updateCourse(courseId: Int, course: PartialCourse): Option[Course] = {
        CourseRepository.update(courseId, course)
    }

    def deleteCourse(courseId: Int): Option[Course] = {
        CourseRepository.delete(courseId)
    }
}