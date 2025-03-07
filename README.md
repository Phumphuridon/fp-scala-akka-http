# Course Management API

This is an Akka HTTP server for managing courses. The API allows for creating, listing, updating, and deleting course records.

## Project Structure

│   Main.scala
│
├───actors
│       CourseActor.scala
│
├───routes
│       CourseRoute.scala
│
├───services
│       CourseService.scala
│
└───types
        Course.scala
        Courses.scala
        PartialCourse.scala

- **`Main.scala`**: Contains the main entry point for the application.
- **`actors/CourseActor.scala`**: Defines the actor that handles course-related operations.
- **`routes/CourseRoute.scala`**: Defines Akka HTTP routes for exposed endpoints related to courses.
- **`services/CourseService.scala`**: Contains the handling course operations.
- **`types/Course.scala`**: Contains the case class and JSON format for the `Course` object.
- **`types/Courses.scala`**: Contains the case class for wrapping a collection of `Course` objects.
- **`types/PartialCourse.scala`**: Contains the case class for updating course details with optional fields.

- ## Endpoints

### 1. `POST /createCourse`
- **Description:** Create a new course.
- **Request Body:** `Course` object with the following fields:
    - `courseId`: Integer, unique identifier of the course.
    - `courseName`: String, name of the course.
    - `courseDescription`: String, description of the course.
    - `instructors`: Array of strings, list of instructors for the course.
    - `courseCredit`: Integer, number of credits for the course.
- **Response:**
    - `201 Created`: Course was successfully created.
    - `409 Conflict`: The course already exists.
    - `500 Internal Server Error`: Error occurred during creation.

### 2. `GET /getAllCourses`
- **Description:** Retrieve a list of all courses.
- **Response:**
    - `200 OK`: List of courses in JSON format.

### 3. `GET /getCourse/{courseId}`
- **Description:** Retrieve a course by its unique ID.
- **URL Parameters:**
    - `courseId`: Integer, the unique identifier of the course.
- **Response:**
    - `200 OK`: Course found.
    - `404 Not Found`: Course not found.
    - `500 Internal Server Error`: Error occurred while fetching the course.

### 4. `PUT /updateCourse/{courseId}`
- **Description:** Update details of an existing course.
- **URL Parameters:**
    - `courseId`: Integer, the unique identifier of the course to be updated.
- **Request Body:** `PartialCourse` object with the following optional fields:
    - `courseName`: Option[String], updated name of the course.
    - `courseDescription`: Option[String], updated description of the course.
    - `instructors`: Option[Seq[String]], updated list of instructors.
    - `courseCredit`: Option[Int], updated number of credits.
- **Response:**
    - `200 OK`: Course updated successfully.
    - `404 Not Found`: Course not found.
    - `500 Internal Server Error`: Error occurred while updating the course.

### 5. `DELETE /deleteCourse/{courseId}`
- **Description:** Delete an existing course.
- **URL Parameters:**
    - `courseId`: Integer, the unique identifier of the course to be deleted.
- **Response:**
    - `200 OK`: Course deleted successfully.
    - `404 Not Found`: Course not found.
    - `500 Internal Server Error`: Error occurred during deletion.

## Example Usage

After starting the server with `sbt run`, you can use the following `curl` commands to interact with the API.

### List all courses:

```bash
curl http://localhost:8000/getAllCourses
```

### Create a new course:
```bash
curl -XPOST http://localhost:8000/createCourse -d '{"courseId": 1, "courseName": "Scala Programming", "courseDescription": "Learn the basics of Scala.", "instructors": ["John Doe", "Jane Smith"], "courseCredit": 4}' -H "Content-Type:application/json"
```

### Get the details of a specific course:
```bash
curl http://localhost:8000/getCourse/1
```

### Update an existing course:
```bash
curl -XPUT http://localhost:8000/updateCourse/1 -d '{"courseName": "Advanced Scala Programming", "courseDescription": "Learn advanced concepts in Scala.", "instructors": ["John Doe"], "courseCredit": 5}' -H "Content-Type:application/json"
```
### Delete a course:
```bash
curl -XDELETE http://localhost:8000/deleteCourse/1
```
