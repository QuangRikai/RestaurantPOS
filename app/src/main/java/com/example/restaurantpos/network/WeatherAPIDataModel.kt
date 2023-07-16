package com.example.restaurantpos.network

import com.google.gson.annotations.SerializedName

// Không hứng thì nó bỏ qua
// Định nghĩa nó hứng mà nó không có  ==> Null thôi
// ==> Có thể không hứng nha

// Tên đặt trùng thì okay, nhưng khác thì cần chỉ định (Y như Room)
data class WeatherAPIDataModel(

    @SerializedName("weather")
    val weather: List<WeatherItem>,

//    @SerializedName("base")
    val base: String,

//    @SerializedName("main")
    val main: MainWeather

    ) {
}