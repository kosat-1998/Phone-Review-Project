package com.example.phoneupdatemyanmar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall {
    companion object {
        const val BASE_URL = "http://mobile.khaingthinkyi.me/api/"
    }

    private val apiInterface : ApiInterface

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun allBrands() = apiInterface.allBrands()
    fun logosList() = apiInterface.logosList()
    fun brandID(id : String) = apiInterface.brandID(id)

}