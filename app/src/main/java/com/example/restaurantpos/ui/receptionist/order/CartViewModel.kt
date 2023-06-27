package com.example.restaurantpos.ui.receptionist.order

import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel: ViewModel() {

    /**Order*/
    fun addOrder(data: OrderEntity){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addOrder(data)
        }
    }
    fun deleteOrder(data: OrderEntity){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.deleteOrder(data)
        }
    }
    fun getOrder(orderId: String){
        DatabaseUtil.getOrder(orderId)
    }


   /**Cart*/
    fun addCartItem(data: CartItemEntity){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addCart(data)
        }
    }
    fun addListCartItem(data: ArrayList<CartItemEntity>){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addListCart(data)
        }
    }

    fun deleteCartItem(data: CartItemEntity){
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.deleteCart(data)
        }
    }
    fun getListCartItem(orderId: String){
        DatabaseUtil.getListCart(orderId)
    }


}