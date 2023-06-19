package com.example.restaurantpos.ui.manager.category

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {
    fun getAllCategory() = DatabaseUtil.getAllCategory()

    fun addCategory(data: CategoryEntity) {
      CoroutineScope(Dispatchers.IO).launch {
          DatabaseUtil.addCategory(data)
      }
    }




}