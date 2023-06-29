package com.example.restaurantpos.ui.receptionist.order

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.util.DatabaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    /**Order*/
    fun addOrder(data: OrderEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addOrder(data)
        }
    }

    fun deleteOrder(data: OrderEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.deleteOrder(data)
        }
    }

    fun getOrder(orderId: String) = DatabaseUtil.getOrder(orderId)


    fun getOrderByTable(tableId: Int) = DatabaseUtil.getOrderByTable(tableId)


    /**Cart*/
    fun addCartItem(data: CartItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addCartItem(data)
        }
    }

    fun addListCartItem(data: List<CartItemEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.addListCartItem(data)
            Log.d("Quangdata", data.toString())
        }
    }

    fun getListCartItemByOrder(orderId: String) = DatabaseUtil.getListCartItemByOrder(orderId)

    fun getListCartItemOnWaiting(orderId: String) = DatabaseUtil.getListCartItemOnWaiting(orderId)

    /** Tại sao xóa {} thì mới dùng observer được?? */
    fun getListCartItemOfKitchen() = DatabaseUtil.getListCartItemOfKitchen()

    fun getListCartItemOfKitchenBySortTime(sortByTimeOfOrder: Int) =
        DatabaseUtil.getListCartItemOfKitchenBySortTime(sortByTimeOfOrder)


    fun deleteCartItem(data: CartItemEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            DatabaseUtil.deleteCart(data)
        }
    }

}