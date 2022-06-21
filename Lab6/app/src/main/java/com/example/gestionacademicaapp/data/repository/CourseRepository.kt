package com.example.gestionacademicaapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.gestionacademicaapp.core.CourseDatabaseHelper
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.data.schemas.Course

class CourseRepository(context: Context) {
    companion object {
        //List
        @SuppressLint("Range")
        fun getCourses(context: Context): ArrayList<CourseModel> {
            val db = CourseDatabaseHelper(context)
            val response = db.allData()
            val coursesArray = ArrayList<CourseModel>()

            while (response.moveToNext()) {
                val id = response.getInt(response.getColumnIndex(Course.PK))
                val description = response.getString(response.getColumnIndex(Course.COLUMN_DESCRIPTION))
                val credits = response.getInt(response.getColumnIndex(Course.COLUMN_CREDITS))

                coursesArray.add(CourseModel(id, description, credits))
            }
            return coursesArray
        }

        //Search by ID
        @SuppressLint("Range")
        fun getCourse(context: Context, id: Int): CourseModel? {
            val response = CourseDatabaseHelper(context).searchData(id)
            var courseResponse: CourseModel? = null;
            if (response.moveToNext()) {
                val id = response.getInt(response.getColumnIndex(Course.PK))
                val description = response.getString(response.getColumnIndex(Course.COLUMN_DESCRIPTION))
                val credits = response.getInt(response.getColumnIndex(Course.COLUMN_CREDITS))

                courseResponse = CourseModel(id, description, credits)
            }
            return courseResponse
        }

        //Insert
        fun insertCourse(context: Context, course: CourseModel): Boolean {
            val db = CourseDatabaseHelper(context)
            val response = db.insertData(course)
            return response > 0
        }

        //Update
        fun updateCourse(context: Context, course: CourseModel): Boolean {
            val response = CourseDatabaseHelper(context).updateData(course)
            return response > 0;
        }

        //Delete
        fun deleteCourse(context: Context, course: CourseModel): Boolean {
            val response = CourseDatabaseHelper(context).deleteData(course.ID)
            return response > 0
        }

    }
}