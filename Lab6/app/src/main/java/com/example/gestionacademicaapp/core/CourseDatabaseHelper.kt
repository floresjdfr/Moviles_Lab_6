package com.example.gestionacademicaapp.core

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.data.schemas.Course

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class CourseDatabaseHelper(context: Context) :
        DatabaseHelper(context) {

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertData(course: CourseModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Course.COLUMN_DESCRIPTION, course.Description)
        contentValues.put(Course.COLUMN_CREDITS, course.Credits)

        return db.insert(Course.TABLE_NAME, null, contentValues)
    }

    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateData(course: CourseModel):
            Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Course.COLUMN_DESCRIPTION, course.Description)
        contentValues.put(Course.COLUMN_CREDITS, course.Credits)

        return db.update(Course.TABLE_NAME, contentValues, "${Course.PK} = ?", arrayOf(course.ID.toString()))
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id : Int) : Int {
        val db = this.writableDatabase
        return db.delete(Course.TABLE_NAME,"${Course.PK} = ?", arrayOf(id.toString()))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    fun allData() : Cursor{
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM ${Course.TABLE_NAME}", null)
    }


    fun searchData (id: Int): Cursor
    {
        val db = this.writableDatabase
        val querySearch = "SELECT * FROM ${Course.TABLE_NAME} WHERE ${Course.PK} = $id"
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