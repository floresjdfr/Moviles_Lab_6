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
        viewModelScope.launch {
            isLoading.postValue(true)
            var result = StudentRepository.getStudents(context)
            //careers.postValue(result)
            students.value= result
            isLoading.postValue(false)
        }
    }

    suspend fun createStudent(context: Context, student: StudentModel) {
        isLoading.postValue(true)
        var result = StudentRepository.insertStudent(context, student)
        isLoading.postValue(false)
    }

//    suspend fun deleteCareer(id: Int): Boolean {
//        return CareerRepository.deleteCareer(id)
//    }
//
//    suspend fun updateCareer(id: Int, career: CareerModel): Boolean {
//        isLoading.postValue(true)
//        var result = CareerRepository.updateCareer(id, career)
//        isLoading.postValue(false)
//        return result
//    }
}