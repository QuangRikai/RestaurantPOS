package com.quang.demo1.network

import com.google.gson.annotations.SerializedName

data class ServiceDataModel(

    @SerializedName("weather")
    val weather : List<WeatherItem>,

    val base : String,

    val main : MainWeather

) {
}