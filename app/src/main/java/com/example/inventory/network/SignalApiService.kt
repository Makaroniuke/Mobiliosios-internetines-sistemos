package com.example.inventory.network


import com.example.inventory.data.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit


import retrofit2.http.GET

import retrofit2.converter.moshi.MoshiConverterFactory


private const val BASE_URL =
    "http://192.168.0.100"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface SignalApiService {

    @GET("users.php")
    suspend fun getUsers():List<Users>
    @GET("measurements.php")
    suspend fun getMeasurements():List<Measurements>
    @GET("strengths.php")
    suspend fun getStrengths():List<Strengths>



}

object SignalApi {
    val retrofitService : SignalApiService by lazy {
        retrofit.create(SignalApiService::class.java)
    }

}