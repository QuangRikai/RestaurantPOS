package com.example.restaurantpos.ui.manager.home

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    fun getRevenueOfDayOfItem(id_item: Int, time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.getRevenueOfDayOfItem(id_item, time)
        }
    }

    fun getRevenueOfDay(time: String) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.getRevenueOfDay(time)
        }
    }
}