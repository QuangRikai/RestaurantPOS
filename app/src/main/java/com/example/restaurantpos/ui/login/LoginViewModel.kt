package com.example.restaurantpos.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.R
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase
import com.example.restaurantpos.ui.main.MainKitchenActivity
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.ui.main.MainReceptionistActivity
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity
import com.example.restaurantpos.util.show
import com.example.restaurantpos.util.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
//    private val view: MutableLiveData<View> = MutableLiveData()
    @SuppressLint("StaticFieldLeak")
    fun checkLogin(context: Context, userName: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginAccountList =
                PosRoomDatabase.getInstance(context).accountDAO().checkLogin(userName, password)
            if (loginAccountList.size < 1) {
                CoroutineScope(Dispatchers.Main).launch {
                    context.showToast("Username or password is wrong!")

/*                    view.value?.findViewById<TextView>(R.id.txtInformLogin)?.text =
                        "Username or password is wrong!"
                    view.value?.findViewById<TextView>(R.id.txtInformLogin)?.show()*/
                }
            } else {
                val acc = loginAccountList[0]
                when (acc.role) {
                    0 -> {
 /*                       SharedPreferencesUtils.setUserName(acc.user_name)
                        SharedPreferencesUtils.setPassword(acc.password)*/
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        context.openActivity(MainManagerActivity::class.java)
                    }

                    1 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        context.openActivity(MainReceptionistActivity::class.java)
                    }

                    2 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        context.openActivity(MainKitchenActivity::class.java)
                    }
                }
            }
        }
    }

    fun addFirstManager(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            PosRoomDatabase.getInstance(context).accountDAO().addAccount(
                AccountEntity(1, "Le Thanh Quang", "quang", "123", 0, true)
            )
//            DatabaseUtil.addAccount(AccountEntity(1, "Manager Quang", "quang", "123", 1, true))
        }
    }
    /*    private fun addAccount() {
            // Add list Account
            CoroutineScope(Dispatchers.IO).launch {
                PosRoomDatabase.getInstance(this@LoginActivity).accountDAO().addListAccount(
                    listOf(
                        AccountEntity(1, "Manager Quang", "quang", "123", 1),
                        AccountEntity(2, "Receptionist Phuong", "phuong", "123", 2),
                        AccountEntity(3, "Kitchen Chuong", "truong", "123", 3)
                    )
                )
            }
            // Add list Table
            CoroutineScope(Dispatchers.IO).launch {
                PosRoomDatabase.getInstance(this@LoginActivity).tableDAO().addListTable(
                    listOf(
                        TableEntity(1, "Table 01"),
                        TableEntity(2, "Table 02"),
                        TableEntity(3, "Table 03"),
                        TableEntity(4, "Table 04"),
                        TableEntity(5, "Table 05"),
                        TableEntity(6, "Table 06"),
                        TableEntity(7, "Table 07"),
                        TableEntity(8, "Table 08"),
                        TableEntity(9, "Table 09")
                    )
                )
            }
        }*/


}