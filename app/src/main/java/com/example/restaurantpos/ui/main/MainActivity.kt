package com.example.restaurantpos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}