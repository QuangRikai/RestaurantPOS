package com.example.restaurantpos.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 1. Change Activity by Intent
fun Context.openActivity(pClass : Class<out Activity>) {
    startActivity(Intent(this, pClass))
}

fun Context.openActivity(pClass : Class<out Activity>, isFinish : Boolean = false) {
    startActivity(Intent(this, pClass))
    if (isFinish) {
        (this as Activity).finish()
    }
}
// 2. hide, show, gone View
fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Context.showToast(string: String){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
}