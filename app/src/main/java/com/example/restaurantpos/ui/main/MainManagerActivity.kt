package com.example.restaurantpos.ui.main

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.restaurantpos.R
import com.example.restaurantpos.databinding.ActivityMainManagerBinding
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.ui.login.LoginActivity
import com.example.restaurantpos.ui.manager.UpdateAccountInfoActivity
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity

class MainManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainManagerBinding
    private lateinit var navController: NavController


    var accountObject: AccountEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Navigation
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_manager) as NavHostFragment
        navController = navHostFragment.navController

        // ToolBar
        // 1. Get Account's Name
        binding.txtLoginAccountName.text = SharedPreferencesUtils.getAccountName()

//        accountObject = AccountEntity.toAccount(requireArguments("accountObject").toString())
        /** 2. Handle popupMenu in Top-Right */
        binding.imgMenuToolBar.setOnClickListener { imgMenu ->
            val popupMenu = PopupMenu(this@MainManagerActivity, imgMenu)
            popupMenu.inflate(R.menu.popup_menu_main_manager)
            popupMenu.setOnMenuItemClickListener { popupMenu ->
                when (popupMenu.itemId) {
                    // Logout
                    R.id.menu_logout -> {
                        openActivity(LoginActivity::class.java, true)
                        true
                    }

                    // Logout
                    R.id.menu_update_info -> {
                        openActivity(UpdateAccountInfoActivity::class.java)
                        true
                    }


                    else -> true
                }
            }

            try {
                val popup = popupMenu::class.java.getDeclaredField("qPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenu)
                menu.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(menu, true)
            } catch (e: Exception){
                e.printStackTrace()
            } finally {
              popupMenu.show()
            }
        }
    }
}