package com.example.restaurantpos.ui.manager.category

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
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

    fun deleteItemOfCategory(itemOfCategory: ItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.deleteItemOfCategory(itemOfCategory)
        }
    }

    fun addListCategoryItem(listCategoryItem: List<ItemEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addListCategoryItem(listCategoryItem)
        }
    }


    fun getAllCategory() = DatabaseUtil.getAllCategory()
    fun getItemOfCategory(item_id: Int) = DatabaseUtil.getItemOfCategory(item_id)

    fun getListCategoryComponentItem(categoryComponentId: Int) =
        DatabaseUtil.getListCategoryComponentItem(categoryComponentId)

    fun getItemByName(name: String) = CoroutineScope(Dispatchers.IO).launch {
        DatabaseUtil.getItemByName(name)
    }


}