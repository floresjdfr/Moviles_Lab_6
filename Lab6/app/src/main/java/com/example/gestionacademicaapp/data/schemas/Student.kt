package com.example.gestionacademicaapp.data.schemas

abstract class Student {
    companion object {
        val TABLE_NAME = "Student"
        val PK = "Pk_Student"
        val COLUMN_NAME = "Name"
        val COLUMN_LASTNAME = "LastName"
        val COLUMN_AGE = "Age"
    }
}