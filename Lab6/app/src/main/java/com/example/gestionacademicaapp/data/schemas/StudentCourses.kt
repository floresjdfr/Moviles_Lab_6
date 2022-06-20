package com.example.gestionacademicaapp.data.schemas

abstract class StudentCourses {
    companion object {
        val TABLE_NAME = "StudentCourses"
        val PK = "Pk_StudentCourses"
        val COLUMN_FK_STUDENT = "Fk_Student"
        val COLUMN_FK_COURSE = "Fk_Course"
    }
}