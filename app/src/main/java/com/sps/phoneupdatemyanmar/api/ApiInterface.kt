package com.sps.phoneupdatemyanmar.api

import com.sps.phoneupdatemyanmar.model_all_brands.AllBrands
import com.sps.phoneupdatemyanmar.model_logos.Logo
import com.sps.phoneupdatemyanmar.model_one_brand.OneBrand
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {


    @GET("specifications")
    fun allBrands() : Call<AllBrands>

    @GET("brands")
    fun logosList() : Call<Logo>

    @GET("getbrand")
    fun brandID(@Query("brand_id")id: String) : Call<OneBrand>

}