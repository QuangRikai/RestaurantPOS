package com.example.restaurantpos.util

object DataUtil {

    fun getStringFromList(data: ArrayList<String>): String {
        var stringResult = ""

        // Tận dụng thằng split để băm String ra
        // Băm ra --> Mã hóa
        // Sau đó vặn ngược trở lại
        for (i in 0..data.size) {
            stringResult += data[i]
            if (i != data.size) {
                // Chuối chắc chắn có path nên sẽ không có thằng || ở bên trong
                stringResult += "||"
            }
        }
        return stringResult
    }

    fun getListFromString(data: String): List<String> {
        return data.split("||")
    }


}