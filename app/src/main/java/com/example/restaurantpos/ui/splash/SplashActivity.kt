package com.example.restaurantpos.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.ActivitySplashBinding
import com.example.restaurantpos.ui.main.MainActivity
import com.example.restaurantpos.util.openActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val DELAY_TIME = 1000L
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLogin()


    }

    private fun checkLogin() {

    }

    private fun startMain(){
        Handler(Looper.getMainLooper()).postDelayed({
            openActivity(MainActivity::class.java, true)
        }, DELAY_TIME)
    }
}