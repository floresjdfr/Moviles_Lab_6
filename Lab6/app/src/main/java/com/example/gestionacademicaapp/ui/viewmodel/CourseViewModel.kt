package com.example.gestionacademicaapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.data.repository.CourseRepository
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    val courses = MutableLiveData<ArrayList<CourseModel>>(ArrayList())
    val isLoading = MutableLiveData<Boolean>()
    fun getCourses(context: Context) {

        isLoading.postValue(true)
        var result = CourseRepository.getCourses(context)
        courses.value = result
        isLoading.postValue(false)

    }

    fun createCourse(context: Context, course: CourseModel) {
        isLoading.postValue(true)
        CourseRepository.insertCourse(context, course)
        isLoading.postValue(false)
    }

    fun deleteCourse(context: Context, course: CourseModel): Boolean {
        return CourseRepository.deleteCourse(context, course)
    }

    fun updateCourse(context: Context, course: CourseModel): Boolean {
        isLoading.postValue(true)
        var result = CourseRepository.updateCourse(context, course)
        isLoading.postValue(false)
        return result
    }
}