package com.example.gestionacademicaapp.core

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

object RetrofitHelper {
    private fun getClient(): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HeaderInterceptor())
        .callTimeout(Duration.ofSeconds(15))
        .build()

    fun getRetrofit(): Retrofit = Retrofit
        .Builder()
        .baseUrl("http://192.168.100.11:5000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()
}