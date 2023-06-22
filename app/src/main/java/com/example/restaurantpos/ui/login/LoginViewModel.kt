package com.example.restaurantpos.ui.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase
import com.example.restaurantpos.ui.main.MainKitchenActivity
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.ui.main.MainReceptionistActivity
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity
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
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        context.openActivity(MainManagerActivity::class.java)
                    }

                    1 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        context.openActivity(MainReceptionistActivity::class.java, bundleOf("NavigateByRole" to acc.role ))
                    }

                    2 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        context.openActivity(MainReceptionistActivity::class.java, bundleOf("NavigateByRole" to acc.role ))
                    }
                }
            }
        }
    }
}