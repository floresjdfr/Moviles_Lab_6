package com.example.gestionacademicaapp.data.schemas

abstract class Course {
    companion object {
        val TABLE_NAME = "Course"
        val PK = "Pk_Course"
        val COLUMN_Description = "Description"
        val COLUMN_CREDITS = "Credits"
    }
}