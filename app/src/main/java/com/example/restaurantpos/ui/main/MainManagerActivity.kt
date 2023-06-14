package com.example.restaurantpos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.ActivityMainManagerBinding

class MainManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainManagerBinding
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_manager) as NavHostFragment
        navController = navHostFragment.navController*/

    }
}