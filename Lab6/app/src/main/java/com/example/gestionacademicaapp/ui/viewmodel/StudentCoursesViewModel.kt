package com.example.gestionacademicaapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.data.model.StudentCourseModel
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.repository.CourseRepository
import com.example.gestionacademicaapp.data.repository.StudentCourseRepository

class StudentCoursesViewModel : ViewModel() {
    val student = MutableLiveData<StudentModel>()
    val studentCourses = MutableLiveData(ArrayList<StudentCourseModel>())
    val availableCourses = MutableLiveData(ArrayList<CourseModel>())
    val isLoading = MutableLiveData<Boolean>()

    fun getStudentCourses(context: Context) {
            isLoading.postValue(true)
            val result = StudentCourseRepository.getStudentCourses(context, this.student.value!!)
            studentCourses.value = result
            isLoading.postValue(false)
    }

    fun getCourses(context: Context) {
        isLoading.postValue(true)
        var result = CourseRepository.getCourses(context)
        availableCourses.value = result
        isLoading.postValue(false)
    }

    fun createStudentCourse(context: Context, student: StudentModel, course: CourseModel) {
        isLoading.postValue(true)
        StudentCourseRepository.insertStudentCourse(context, student, course)
        isLoading.postValue(false)
    }

    fun unrollCourse(context: Context, studentCourse: StudentCourseModel): Boolean {
         return StudentCourseRepository.deleteStudent(context, studentCourse)
    }
//    fun updateStudent(context: Context, student: StudentModel): Boolean {
//        isLoading.postValue(true)
//        var result = StudentRepository.updateStudent(context, student)
//        isLoading.postValue(false)
//        return result
//    }
}