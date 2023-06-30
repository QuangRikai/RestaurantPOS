package com.example.restaurantpos.ui.manager.customer

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerViewModel: ViewModel() {

    fun getListCustomerByPhone(phone: String) = DatabaseUtil.getListCustomerByPhone(phone)

    fun getCustomerByPhone(phone: String) = DatabaseUtil.getCustomerByPhone(phone)

    fun addCustomer(data: CustomerEntity) {
        CoroutineScope(Dispatchers.IO).launch{
            DatabaseUtil.addCustomer(data)
        }
    }

}