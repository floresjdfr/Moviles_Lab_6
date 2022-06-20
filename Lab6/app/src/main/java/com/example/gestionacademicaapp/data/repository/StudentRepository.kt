package com.example.gestionacademicaapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.gestionacademicaapp.core.StudentDatabaseHelper
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.schemas.Student
import okhttp3.internal.notifyAll

class StudentRepository(context: Context) {
    companion object {
        fun insertStudent(context: Context, student: StudentModel): Boolean {

            val db = StudentDatabaseHelper(context)
            val response = db.insertData(student)
            return response > 0
        }

        @SuppressLint("Range")
        fun getStudents(context: Context): ArrayList<StudentModel> {
            val db = StudentDatabaseHelper(context)
            val response = db.allData()
            val studentsArray = ArrayList<StudentModel>()

            while(response.moveToNext()){
                val id = response.getInt(response.getColumnIndex(Student.PK))
                val name = response.getString(response.getColumnIndex(Student.COLUMN_NAME))
                val lastname = response.getString(response.getColumnIndex(Student.COLUMN_LASTNAME))
                val age = response.getInt(response.getColumnIndex(Student.COLUMN_AGE))

                studentsArray.add(StudentModel(id, name, lastname, age, ArrayList()))
            }

            return studentsArray
        }
    }
}