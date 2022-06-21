package com.example.gestionacademicaapp.core

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestionacademicaapp.data.model.StudentCourseModel
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.schemas.Course
import com.example.gestionacademicaapp.data.schemas.Student
import com.example.gestionacademicaapp.data.schemas.StudentCourses

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class StudentCoursesDatabaseHelper(context: Context) :
    DatabaseHelper(context) {

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertData(studentCourse: StudentCourseModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(StudentCourses.COLUMN_FK_STUDENT, studentCourse.Student.ID)
        contentValues.put(StudentCourses.COLUMN_FK_COURSE, studentCourse.Course?.ID)

        return db.insert(StudentCourses.TABLE_NAME, null, contentValues)
    }

//    /**
//     * Let's create  a method to update a row with new field values.
//     */
//    fun updateData(studentCourse: StudentCourse):
//            Int {
//        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(StudentCourses.COLUMN_FK_STUDENT, studentCourse.Pk_Student)
//        contentValues.put(StudentCourses.COLUMN_FK_COURSE, studentCourse.Pk_Course)
//
//        return db.update(
//            StudentCourses.TABLE_NAME,
//            contentValues,
//            "${StudentCourses.PK} = ?",
//            arrayOf(studentCourse.ID.toString())
//        )
//    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(StudentCourses.TABLE_NAME, "${StudentCourses.PK} = ?", arrayOf(id.toString()))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    fun allData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM ${StudentCourses.TABLE_NAME}", null)
    }

//    fun searchData(id: Int): Cursor {
//        val db = this.writableDatabase
//        val querySearch = "SELECT * FROM ${StudentCourses.TABLE_NAME} WHERE ${StudentCourses.PK} = $id"
//        return db.rawQuery(querySearch, null)
//    }

    fun getCourses(id: Int): Cursor {
        val db = this.writableDatabase
        val querySearch = "SELECT * FROM ${StudentCourses.TABLE_NAME} " +
                "LEFT JOIN ${Course.TABLE_NAME} " +
                "ON ${StudentCourses.TABLE_NAME}.${StudentCourses.COLUMN_FK_COURSE} = ${Course.TABLE_NAME}.${Course.PK} " +
                "LEFT JOIN ${Student.TABLE_NAME} " +
                "ON ${StudentCourses.TABLE_NAME}.${StudentCourses.COLUMN_FK_STUDENT} = ${Student.TABLE_NAME}.${Student.PK} " +
                "WHERE ${StudentCourses.COLUMN_FK_STUDENT} = $id "
        return db.rawQuery(querySearch, null)
    }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "lab6"
    }
}
//end