package com.example.restaurantpos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.ActivityMainManagerBinding
import com.example.restaurantpos.ui.login.LoginActivity
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity

class MainManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainManagerBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_manager) as NavHostFragment
        navController = navHostFragment.navController

        // ToolBar
        binding.txtLoginAccountName.text = SharedPreferencesUtils.getAccountName()
        binding.imgMenuToolBar.setOnClickListener {


            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.popup_menu_main_manager)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_logout -> {
                        openActivity(LoginActivity::class.java,true)
                        true
                    }
                    else -> true
                }
            }
        }

    }



}