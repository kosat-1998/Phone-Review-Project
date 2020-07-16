package com.sps.phoneupdatemyanmar.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sps.phoneupdatemyanmar.api.ApiCall
import com.sps.phoneupdatemyanmar.model_logos.Brand
import com.sps.phoneupdatemyanmar.model_logos.Logo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoViewModel : ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    fun loading() = loading
    private val apiCall = ApiCall()

    var resultBrandList: MutableLiveData<ArrayList<Brand>> = MutableLiveData()

    fun brandList() = resultBrandList

    fun loadBrands(context: Context?) {
        val call = apiCall.logosList()

        call.enqueue(object : Callback<Logo> {
            override fun onFailure(call: Call<Logo>, t: Throwable) {
                loading.value = true
                Log.i("Error", "Load Fail Brands")
            }

            override fun onResponse(call: Call<Logo>, response: Response<Logo>) {

                loading.value = true
                response.isSuccessful.let {
                    val result: List<Brand> = response.body()?.brands ?: emptyList()
                    if (result.isEmpty()) {
                        Toast.makeText(
                            context, "Loading Data Error From Web Server!! Try Again Later!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        resultBrandList.value = result as ArrayList<Brand>
                    }

                }
            }

        })
    }
}