package com.example.gestionacademicaapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    val students = MutableLiveData<ArrayList<StudentModel>>(ArrayList())
    val isLoading = MutableLiveData<Boolean>()
    fun getStudents(context: Context) {

        isLoading.postValue(true)
        var result = StudentRepository.getStudents(context)
        students.value = result
        isLoading.postValue(false)

    }

    fun createStudent(context: Context, student: StudentModel) {
        isLoading.postValue(true)
        StudentRepository.insertStudent(context, student)
        isLoading.postValue(false)
    }

    fun deleteStudent(context: Context, student: StudentModel): Boolean {
        return StudentRepository.deleteStudent(context, student)
    }

    fun updateStudent(context: Context, student: StudentModel): Boolean {
        isLoading.postValue(true)
        var result = StudentRepository.updateStudent(context, student)
        isLoading.postValue(false)
        return result
    }
}