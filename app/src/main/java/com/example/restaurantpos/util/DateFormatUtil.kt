package com.example.restaurantpos.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateFormatUtil {
    const val formatDate = "dd/MM/yyyy"

    @SuppressLint("SimpleDateFormat")
    fun convertStringToDate(data : String) : Date {
        val dateFormat = SimpleDateFormat(formatDate)
        val date = dateFormat.parse(data)
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToString(data : Date) : String {
        val dateFormat = SimpleDateFormat(formatDate)
        return dateFormat.format(data)
    }

}