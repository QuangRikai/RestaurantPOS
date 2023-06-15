package com.example.restaurantpos.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.example.restaurantpos.base.BaseActivity
import com.example.restaurantpos.databinding.ActivitySplashBinding
import com.example.restaurantpos.ui.login.LoginActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val DELAY_TIME = 1500L
    override fun initOnCreate() {
        checkLogin()
    }

    private fun checkLogin() {
        startLoginActivity()
    }

    private fun startLoginActivity(){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, DELAY_TIME)
    }

    override fun getInflaterViewBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}