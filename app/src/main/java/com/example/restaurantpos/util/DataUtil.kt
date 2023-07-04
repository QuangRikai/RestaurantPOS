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


}