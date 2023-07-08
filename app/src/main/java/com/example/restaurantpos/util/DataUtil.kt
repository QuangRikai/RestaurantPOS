package com.example.restaurantpos.util

import java.math.BigInteger
import java.security.MessageDigest
import java.util.Calendar

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


    /** I. For Shift Adapter */
    // plus là cái quần què gì???

    val numberOfDayInAMonthOfNotLeapYear =
        listOf<Int>(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val numberOfDayInAMonthOfLeapYear =
        listOf<Int>(0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    /** I.1　For　Plus */
    fun plusDayReturnDay(year: Int, month: Int, day: Int, plus: Int): Int {
        val nowPositionDay: Int = day + plus

        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            // Đủ diều kiện sang tháng
            if (nowPositionDay > numberOfDayInAMonthOfLeapYear[month]) {
                return nowPositionDay - numberOfDayInAMonthOfLeapYear[month]
            } else {
                return nowPositionDay
            }
        } else {
            if (nowPositionDay > numberOfDayInAMonthOfNotLeapYear[month]) {
                return nowPositionDay - numberOfDayInAMonthOfNotLeapYear[month]
            } else {
                return nowPositionDay
            }
        }
    }

    fun plusDayReturnMonth(year: Int, month: Int, day: Int, plus: Int): Int {
        val nowPositionDay: Int = day + plus

        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            if (nowPositionDay > numberOfDayInAMonthOfLeapYear[month]) {
                return month + 1
            } else {
                return month
            }
        } else {
            if (nowPositionDay > numberOfDayInAMonthOfNotLeapYear[month]) {
                return month + 1
            } else {
                return month
            }
        }
    }

    fun plusDayReturnYear(year: Int, month: Int, day: Int, plus: Int): Int {
        val nowPositionDay: Int = day + plus

        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            if (nowPositionDay > numberOfDayInAMonthOfLeapYear[month] && month == 12) {
                return year + 1
            } else {
                return year
            }
        } else {
            if (nowPositionDay > numberOfDayInAMonthOfNotLeapYear[month] && month == 12) {
                return year + 1
            } else {
                return year
            }
        }
    }

    /** I.２　For　Minus */
    fun minusDayReturnDay(year: Int, month: Int, day: Int, plus: Int): Int {
        if (plus >= day) {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                return numberOfDayInAMonthOfLeapYear[month - 1] + (day - plus)
            } else {
                return numberOfDayInAMonthOfNotLeapYear[month - 1] + (day - plus)
            }

        } else {
            return day - plus
        }
    }

    fun minusDayReturnMonth(year: Int, month: Int, day: Int, plus: Int): Int {
        if (plus >= day) {
            if (month == 1) {
                return 12
            } else {
                return month - 1
            }

        } else {
            return month
        }
    }

    fun minusDayReturnYear(year: Int, month: Int, day: Int, plus: Int): Int {
        if (plus >= day) {
            if (month == 1) {
                return year - 1
            } else {
                return year
            }

        } else {
            return year
        }
    }


    /** Token */


    fun getDateToToken() : String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.AM_PM, 1)
        val nam = calendar.get(Calendar.YEAR)
        val thang = calendar.get(Calendar.MONTH) + 1
        val ngay = calendar.get(Calendar.DATE)
        val gio = calendar.get(Calendar.HOUR_OF_DAY)
        val phut = calendar.get(Calendar.MINUTE)
        if (gio == 22) {
            return String.format("%d_%02d_%02d_%02d_%02d", nam, thang, ngay + 1, 0, phut)
        }
        return String.format("%d_%02d_%02d_%02d_%02d", nam, thang, ngay, gio + 2, phut)
    }

    fun getNowForToken() : String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.AM_PM, 1)
        val nam = calendar.get(Calendar.YEAR)
        val thang = calendar.get(Calendar.MONTH)
        val ngay = calendar.get(Calendar.DATE)
        val gio = calendar.get(Calendar.HOUR_OF_DAY)
        val phut = calendar.get(Calendar.MINUTE)
        return String.format("%d_%02d_%02d_%02d_%02d", nam, thang, ngay, gio, phut)
    }

    fun getDateCreateToken() : String {
        return convertToMD5(DateFormatUtil.getTimeForOrderId())
    }
//    const val formatDateAndMore = "yyyy/MM/dd  HH:mm:ss"
//    Từ 1 thằng không trùng, lại tạo thêm 1 thằng không trùng, vậy nên không sợ bị trùng
    fun convertToMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}