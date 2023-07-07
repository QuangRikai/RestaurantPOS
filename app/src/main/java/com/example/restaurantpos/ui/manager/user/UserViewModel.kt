package com.example.restaurantpos.ui.manager.user

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    fun getAllUser() = DatabaseUtil.getAllUser()
    fun getAllUserActive() = DatabaseUtil.getAllUserActive()

    fun getAllUser(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            PosRoomDatabase.getInstance(context).accountDAO().getAllAccount()
        }
    }

    fun getStaffByName(staffName: String) =DatabaseUtil.getStaffByName(staffName)
    fun getAccountById(account_id: Int)  =DatabaseUtil.getAccountById(account_id)


    fun addUser(context: Context, user: AccountEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            PosRoomDatabase.getInstance(context).accountDAO().addAccount(user)
        }
    }


}