package com.example.gestionacademicaapp.data.model

data class StudentCourseModel(override var ID: Int, var Student: StudentModel, var Course: CourseModel?) : Base()