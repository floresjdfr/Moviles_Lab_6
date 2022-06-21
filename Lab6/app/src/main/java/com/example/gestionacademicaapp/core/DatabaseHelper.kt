package com.example.gestionacademicaapp.core

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gestionacademicaapp.data.schemas.Course
import com.example.gestionacademicaapp.data.schemas.Student
import com.example.gestionacademicaapp.data.schemas.StudentCourses

open class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, CourseDatabaseHelper.DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE ${Course.TABLE_NAME} (" +
                "${Course.PK} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Course.COLUMN_DESCRIPTION} TEXT," +
                "${Course.COLUMN_CREDITS} INTEGER)")

        db.execSQL("CREATE TABLE ${Student.TABLE_NAME} (" +
                "${Student.PK} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Student.COLUMN_NAME} TEXT," +
                "${Student.COLUMN_LASTNAME} TEXT," +
                "${Student.COLUMN_AGE} INTEGER)")

        db.execSQL(
            "CREATE TABLE ${StudentCourses.TABLE_NAME} (" +
                    "${StudentCourses.PK} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${StudentCourses.COLUMN_FK_STUDENT} TEXT," +
                    "${StudentCourses.COLUMN_FK_COURSE} INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + StudentCourses.TABLE_NAME)
        onCreate(db)
    }
}