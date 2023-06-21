package com.example.restaurantpos.util

import android.content.Context
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.dao.CategoryDAO
import com.example.restaurantpos.db.dao.ItemDAO
import com.example.restaurantpos.db.dao.TableDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase

object DatabaseUtil {

    lateinit var appDAO: AppDAO
    lateinit var accountDAO: AccountDAO
    lateinit var categoryDAO: CategoryDAO
    lateinit var itemDAO: ItemDAO
    lateinit var tableDAO: TableDAO

    fun init(context: Context){
        appDAO = PosRoomDatabase.getInstance(context).appDAO()
        categoryDAO = PosRoomDatabase.getInstance(context).categoryDAO()
        itemDAO = PosRoomDatabase.getInstance(context).itemDAO()
        tableDAO = PosRoomDatabase.getInstance(context).tableDAO()
    }

    /** 1. USER MANAGEMENT  */

    fun addAccount(accountEntity: AccountEntity) = appDAO.addAccount(accountEntity)
    fun getAllUser() = appDAO.getAllUser()

    /** 2. CATEGORY, ITEM MANAGEMENT  */

    fun getAllCategory() = categoryDAO.getAllCategory()
    fun addCategory(data: CategoryEntity) = categoryDAO.addCategory(data)
    fun addCategoryItem(data: ItemEntity) = itemDAO.addCategoryItem(data)
    fun addListCategoryItem(listData: List<ItemEntity>) = itemDAO.addListCategoryItem(listData)
    fun getListCategoryComponentItem(categoryComponentId: Int) = itemDAO.getListCategoryComponentItem(categoryComponentId)


    /** 3. TABLE MANAGEMENT  */
    fun addTable(data: TableEntity) = tableDAO.addTable(data)
    fun getAllTable() = tableDAO.getAllTable()

}