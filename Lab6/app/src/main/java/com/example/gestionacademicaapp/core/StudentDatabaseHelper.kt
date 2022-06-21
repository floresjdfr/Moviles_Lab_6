package com.example.gestionacademicaapp.core

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.schemas.Student

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class StudentDatabaseHelper(context: Context) :
    DatabaseHelper(context) {

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertData(student: StudentModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Student.COLUMN_NAME, student.Name)
        contentValues.put(Student.COLUMN_LASTNAME, student.LastName)
        contentValues.put(Student.COLUMN_AGE, student.Age)
        return db.insert(Student.TABLE_NAME, null, contentValues)
    }

    /**
     * Let's create  a method to update a row with new field values.
     */
    fun updateData(student: StudentModel):
            Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Student.COLUMN_NAME, student.Name)
        contentValues.put(Student.COLUMN_LASTNAME, student.LastName)
        contentValues.put(Student.COLUMN_AGE, student.Age)

        return db.update(Student.TABLE_NAME, contentValues, "${Student.PK} = ?", arrayOf(student.ID.toString()))
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(Student.TABLE_NAME, "${Student.PK} = ?", arrayOf(id.toString()))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    fun allData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM ${Student.TABLE_NAME}", null)
    }


    fun searchData(id: Int): Cursor {
        val db = this.writableDatabase
        val querySearch = "SELECT * FROM ${Student.TABLE_NAME} WHERE ${Student.PK} = $id"
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