package com.example.restaurantpos.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.restaurantpos.R
import com.example.restaurantpos.base.BaseActivity
import com.example.restaurantpos.databinding.ActivityMainManagerBinding
import com.example.restaurantpos.databinding.ActivityMainReceptionistBinding
import com.example.restaurantpos.ui.login.LoginActivity
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity

class MainReceptionistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainReceptionistBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainReceptionistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /** Navigation */
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_receptionist) as NavHostFragment
        navController = navHostFragment.navController

    }


    /*
        // Khoi tao Menu
        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.sub_menu, menu)
            */
    /*
            menu 1: Directory containing menu.xml
            menu 2: menu.xml <== Chi can thay doi nay de update SUB-MENU
            menu 3: Parameter from onCreateOptionsMenu(...)
             *//*

        return true
    }

    // Xy ly su kien <== item in Menu. Use when() duyet all items in menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuExit -> finish()
            R.id.menuHome -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
            R.id.menuSearch -> Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
*/

}