package com.example.restaurantpos.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.R
import com.example.restaurantpos.db.roomdb.PosRoomDatabase
import com.example.restaurantpos.ui.main.MainManagerActivity
import com.example.restaurantpos.ui.main.MainReceptionistActivity
import com.example.restaurantpos.util.DatabaseUtil
import com.example.restaurantpos.util.SharedPreferencesUtils
import com.example.restaurantpos.util.openActivity
import com.example.restaurantpos.util.show
import com.example.restaurantpos.util.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    //    private val view: MutableLiveData<View> = MutableLiveData()

    fun checkLoginAccountDAO(userName: String, password: String) =
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.checkLogin(userName, password)
        }


    @SuppressLint("StaticFieldLeak")
    fun checkLogin(context: Context, textView: TextView, userName: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginAccountList =
                PosRoomDatabase.getInstance(context).accountDAO().checkLogin(userName, password)
            if (loginAccountList.size < 1) {
                CoroutineScope(Dispatchers.Main).launch {
                    textView.text = "Username or password is wrong!"
                    textView.show()
                }
            } else {
                val acc = loginAccountList[0]
                when (acc.role_id) {
                    0 -> {
                        /*                       SharedPreferencesUtils.setUserName(acc.user_name)
                        SharedPreferencesUtils.setPassword(acc.password)*/
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        SharedPreferencesUtils.setAccountRole(acc.role_id)
                        context.openActivity(MainManagerActivity::class.java)
                    }

                    1 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        SharedPreferencesUtils.setAccountRole(acc.role_id)
                        context.openActivity(
                            MainReceptionistActivity::class.java,
                            bundleOf("NavigateByRole" to acc.role_id)
                        )
                    }

                    2 -> {
                        SharedPreferencesUtils.setAccountName(acc.account_name)
                        SharedPreferencesUtils.setAccountId(acc.account_id)
                        SharedPreferencesUtils.setAccountRole(acc.role_id)
                        context.openActivity(
                            MainReceptionistActivity::class.java,
                            bundleOf("NavigateByRole" to acc.role_id)
                        )
                    }
                }
            }
        }
//}

//    fun addToken(data: TokenEntity) {
//        CoroutineScope(Dispatchers.IO).launch {
//            DatabaseUtil.addToken(data)
//        }
//    }
//
//    fun checkToken(token: String, now: String) = DatabaseUtil.checkToken(token, now)
    }
}
