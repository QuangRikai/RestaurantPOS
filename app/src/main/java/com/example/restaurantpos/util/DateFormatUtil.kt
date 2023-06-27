package com.example.restaurantpos.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatUtil {
    const val formatDate = "yyyy/MM/dd"
    const val formatDateAndMore = "yyyy/MM/dd  HH:mm:ss"

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

    fun getTimeForOrderId(): String {
        val simpleDateFormat = SimpleDateFormat(formatDateAndMore, Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

}