package com.example.phoneupdatemyanmar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phoneupdatemyanmar.api.ApiCall
import com.example.phoneupdatemyanmar.model_one_brand.OneBrand
import com.example.phoneupdatemyanmar.model_one_brand.Specificate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneBrandViewModel : ViewModel() {

    val apiCall = ApiCall()

    var resultID: MutableLiveData<ArrayList<Specificate>> = MutableLiveData()
    fun oneBrand() = resultID

    fun loadID(id: String) {
        val call = apiCall.brandID(id)

        call.enqueue(object : Callback<OneBrand> {
            override fun onFailure(call: Call<OneBrand>, t: Throwable) {
                Log.i("Error", "Loading Fail ID")
            }

            override fun onResponse(call: Call<OneBrand>, response: Response<OneBrand>) {
                response.isSuccessful.let {
                    val result: List<Specificate> = response.body()?.specificates ?: emptyList()
                    resultID.value = result as ArrayList<Specificate>
                }
            }

        })


    }

}