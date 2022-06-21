package com.example.gestionacademicaapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.gestionacademicaapp.core.StudentCoursesDatabaseHelper
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.data.model.StudentCourseModel
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.schemas.Course
import com.example.gestionacademicaapp.data.schemas.Student
import com.example.gestionacademicaapp.data.schemas.StudentCourses

class StudentCourseRepository(context: Context) {
    companion object {
        //List
        @SuppressLint("Range")
        fun getStudentCourses(context: Context, student: StudentModel): ArrayList<StudentCourseModel> {
            val db = StudentCoursesDatabaseHelper(context)
            val response = db.getCourses(student.ID)
            val studentCoursesArray = ArrayList<StudentCourseModel>()

            while (response.moveToNext()) {

                val id = response.getInt(response.getColumnIndex(StudentCourses.PK))

                val studentId = response.getInt(response.getColumnIndex(Student.PK))
                val name = response.getString(response.getColumnIndex(Student.COLUMN_NAME))
                val lastname = response.getString(response.getColumnIndex(Student.COLUMN_LASTNAME))
                val age = response.getInt(response.getColumnIndex(Student.COLUMN_AGE))

                val courseId = response.getInt(response.getColumnIndex(Course.PK))
                val description = response.getString(response.getColumnIndex(Course.COLUMN_DESCRIPTION))
                val credits = response.getInt(response.getColumnIndex(Course.COLUMN_CREDITS))

                val student = StudentModel(studentId, name, lastname, age, ArrayList())
                val course = CourseModel(courseId, description, credits)
                val studentCourse = StudentCourseModel(id, student, course)

                studentCoursesArray.add(studentCourse)
            }

            return studentCoursesArray
        }


        //Insert
        fun insertStudentCourse(context: Context, student: StudentModel, course: CourseModel): Boolean {
            val db = StudentCoursesDatabaseHelper(context)
            val studentCourse = StudentCourseModel(0, student, course)
            val response = db.insertData(studentCourse)
            return response > 0
        }

//        //Update
//        fun updateStudent(context: Context, student: StudentModel): Boolean {
//            val response = StudentDatabaseHelper(context).updateData(student)
//            return response > 0;
//        }
//
        //Delete
        fun deleteStudent(context: Context, studentCourse: StudentCourseModel): Boolean {
            val response = StudentCoursesDatabaseHelper(context).deleteData(studentCourse.ID)
            return response > 0
        }

    }
}