package com.example.restaurantpos.ui.manager.customer

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel: ViewModel() {

    fun getListCustomerByPhoneForSearch(phone: String) = DatabaseUtil.getListCustomerByPhoneForSearch(phone)
    fun getListCustomerByPhoneForAdd(phone: String) = DatabaseUtil.getListCustomerByPhoneForAdd(phone)
    fun getListCustomer() = DatabaseUtil.getListCustomer()


    fun addCustomer(data: CustomerEntity) {
        CoroutineScope(Dispatchers.IO).launch{
            DatabaseUtil.addCustomer(data)
        }
    }


}