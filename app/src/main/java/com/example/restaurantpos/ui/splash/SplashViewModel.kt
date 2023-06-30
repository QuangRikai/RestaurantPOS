package com.example.restaurantpos.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.TableEntity
import com.example.restaurantpos.db.roomdb.PosRoomDatabase
import com.example.restaurantpos.ui.manager.category.CategoryViewModel
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    fun addAccount(accountEntity: AccountEntity){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.accountDAO.addAccount(accountEntity)
        }
    }

    fun addListAccount(listAccount: List<AccountEntity>){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.accountDAO.addListAccount(listAccount)
        }
    }

    fun addUser(context: Context, user: AccountEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            PosRoomDatabase.getInstance(context).accountDAO().addAccount(user)
        }
    }

    fun addCategory(data: CategoryEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addCategory(data)
        }
    }

    fun addCategoryItem(categoryItem: ItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addCategoryItem(categoryItem)
        }
    }

    fun addListCategoryItem(listCategoryItem: List<ItemEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addListCategoryItem(listCategoryItem)
        }
    }

    fun addTable(context: Context, table: TableEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            PosRoomDatabase.getInstance(context).tableDAO().addTable(table)
        }
    }

    fun addCustomer(data: CustomerEntity) {
        CoroutineScope(Dispatchers.IO).launch{
            DatabaseUtil.addCustomer(data)
        }
    }

}