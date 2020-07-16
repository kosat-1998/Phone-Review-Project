package com.sps.phoneupdatemyanmar.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sps.phoneupdatemyanmar.api.ApiCall
import com.sps.phoneupdatemyanmar.model_all_brands.AllBrands
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllBrandsViewModel : ViewModel(){
    var loading: Boolean = false

    val apiCall = ApiCall()

    var resultAllBrands: MutableLiveData<ArrayList<Specificate>> = MutableLiveData()

    fun allBrands() = resultAllBrands

    fun loadAllBrands(context: Context?) {
        val callAll = apiCall.allBrands()

        callAll.enqueue(object : Callback<AllBrands> {
            override fun onFailure(call: Call<AllBrands>, t: Throwable) {
                Log.i("Error", "Load Fail All Brands")

            }

            override fun onResponse(call: Call<AllBrands>, response: Response<AllBrands>) {
                loading = true
                Log.i("Success", "Load Success All Brands")

                response.isSuccessful.let {
                    val resultAll: List<Specificate> = response.body()?.specificates ?: emptyList()
                    if (resultAll.isEmpty()) {
                        Toast.makeText(context, "Try Again Later", Toast.LENGTH_LONG).show()
                    } else {
                        resultAllBrands.value = resultAll as ArrayList<Specificate>


                    }

                }
            }

        })


    }
}