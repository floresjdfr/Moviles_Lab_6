package com.example.gestionacademicaapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CareerViewModel : ViewModel() {
    val careers = MutableLiveData<ArrayList<CareerModel>>(ArrayList())
    val isLoading = MutableLiveData<Boolean>()
    fun getCareers() {
        viewModelScope.launch {
            isLoading.postValue(true)
            var result = CareerRepository.getCareers()
            //careers.postValue(result)
            careers.value= result
            isLoading.postValue(false)
        }
    }

    suspend fun createCareer(career: CareerModel) {
        isLoading.postValue(true)
        var result = CareerRepository.createCareer(career)
        isLoading.postValue(false)
    }

    suspend fun deleteCareer(id: Int): Boolean {
        return CareerRepository.deleteCareer(id)
    }

    suspend fun updateCareer(id: Int, career: CareerModel): Boolean {
        isLoading.postValue(true)
        var result = CareerRepository.updateCareer(id, career)
        isLoading.postValue(false)
        return result
    }
}