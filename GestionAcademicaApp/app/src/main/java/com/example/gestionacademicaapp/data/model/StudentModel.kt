package com.example.gestionacademicaapp.data.model

data class StudentModel(override var ID: Int, var Name: String, var LastName: String, var Age: Int, var Courses: ArrayList<CourseModel>): Base()
