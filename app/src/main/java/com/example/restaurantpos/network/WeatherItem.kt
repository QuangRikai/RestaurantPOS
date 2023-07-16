package com.example.restaurantpos.network

data class WeatherItem(
    // Nên để Long để tránh những trường hợp không đáng có như: Giá trị demo mình nhìn được nó quá bé ==> Mình hiểu nhầm ấy
   var id : Long,
   var main: String,
   var description: String,
   var icon: String
) {
}