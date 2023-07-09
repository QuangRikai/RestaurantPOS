package com.quang.demo1.network

import retrofit2.http.GET

interface ApiInterface {

    @GET("data/2.5/weather")
    fun getWeather(
        @retrofit2.http.Query("lat") lat: Double,
        @retrofit2.http.Query("lon") long: Double,
        @retrofit2.http.Query("appid") appid: String,
    ): retrofit2.Call<ServiceDataModel>

}