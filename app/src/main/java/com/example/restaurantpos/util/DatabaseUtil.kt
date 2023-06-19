package com.example.restaurantpos.util

import android.content.Context
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.dao.CategoryDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase

object DatabaseUtil {

    lateinit var appDAO: AppDAO
    lateinit var accountDAO: AccountDAO
    lateinit var categoryDAO: CategoryDAO

    fun init(context: Context){
        appDAO = PosRoomDatabase.getInstance(context).appDAO()
        categoryDAO = PosRoomDatabase.getInstance(context).categoryDAO()
    }

    /**
     * 1. USER MANAGEMENT
      */

    fun addAccount(accountEntity: AccountEntity) = appDAO.addAccount(accountEntity)
    fun getAllUser() = appDAO.getAllUser()


    /**
     * 2. CATEGORY MANAGEMENT
     */
    fun getAllCategory() = categoryDAO.getAllCategory()
    fun addCategory(data: CategoryEntity) = categoryDAO.addCategory(data)


}