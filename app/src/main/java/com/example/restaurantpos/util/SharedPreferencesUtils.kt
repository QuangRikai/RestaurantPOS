package com.example.restaurantpos.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

// Lưu lại tất cả các biến lại cho mình.
// Cũng giống như DB cần mục để lưu trữ --> Này giống như Sources Name
// Kiểu tên đề mục ấy. Thằng nào chung mục lưu trữ thì sẽ lưu cùng vào với nhau.

//Shared Preferences là nơi bạn có thể lưu trữ các thông tin dưới dạng key-value được xây dựng sẵn trong hệ điều hành Android.
object SharedPreferencesUtils {
    // Những biến cố định dùng lưu trữ chuỗi --> Dễ quản lý.
    const val PER_NAME = "data_app_restaurantPOS"


    lateinit var pref: SharedPreferences

    fun init(context: Context) {
        //Nhận lấy shared preference instance có tên là "pref"
        pref = context.getSharedPreferences(PER_NAME, Context.MODE_PRIVATE)
    }

    //pref.edit().putString(key, "value").apply()


    // User Name
    @SuppressLint("CommitPrefEdits")
    fun setUserName(data: String) {
        pref.edit().putString(Constant.USER_NAME, data)
    }

    @SuppressLint("CommitPrefEdits")
    fun getUserName(): String {
        return pref.getString(Constant.USER_NAME, "").toString()
    }

    // Password
    @SuppressLint("CommitPrefEdits")
    fun setPassword(data: String) {
        pref.edit().putString(Constant.PASSWORD, data)
    }

    @SuppressLint("CommitPrefEdits")
    fun getPassword(): String {
        return pref.getString(Constant.PASSWORD, "").toString()
    }


}