package com.example.restaurantpos.util

import android.content.Context
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase

object DatabaseUtil {

    lateinit var appDAO: AppDAO

    fun init(context: Context){
        appDAO = PosRoomDatabase.getInstance(context).appDAO()
    }

    // New Skill: Gán hàm bằng hàm
    // Tốn công đưa các lệnh DAO vào đây nhưng lại dễ nhìn hơn.
    fun addAccount(accountEntity: AccountEntity) = appDAO.addAccount(accountEntity)
    fun getAllUser() = appDAO.getAllUser()

}