package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CartItemEntity

@Dao
interface CartItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCartItem(data: CartItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCartItem(data: List<CartItemEntity>): List<Long>

    @Delete
    fun deleteCartItem(data: CartItemEntity): Int

    // Get ListOrder Of OrderedTable, By order_id (Bill)
    @Query("SELECT * FROM cart_item WHERE order_id = :order_id")
    fun getListCartItemByOrder(order_id: String): LiveData<MutableList<CartItemEntity>>

//    cart_item_status = 0: Những thứ vẫn chưa làm thì cho phép Edit/Delete
    @Query("SELECT * FROM cart_item WHERE order_id = :order_id AND cart_item_status = 0")
    fun getListCartItemOnWaiting(order_id: String): LiveData<MutableList<CartItemEntity>>

    @Query("SELECT * FROM cart_item WHERE cart_item_status < 4")
    fun getListCartItemOfKitchen(): LiveData<MutableList<CartItemEntity>>

    // Đỉnh: Case ở đây chính là If-Else
    /*
    Sort theo Order_id (Order_create_id)
    sortByTimeOfOrder = 0 --> Không Sort/Giữ nguyên tăng dần      Ascending
    sortByTimeOfOrder = 1 --> Sort ngược (Giảm dần)               Descending
    sortByTimeOfOrder = 2 --> Bỏ qua

    Con số status có thể quyết định việc bỏ đi hoặc không á.
    */
    @Query(
        "SELECT * FROM cart_item WHERE cart_item_status < 3  \n" +
                "ORDER BY \n" +
                "CASE WHEN :sortByTimeOfOrder = 0 THEN order_id END ASC, \n" +
                "CASE WHEN :sortByTimeOfOrder = 1 THEN order_id END DESC"
    )
    fun getListCartItemOfKitchenBySortTime(sortByTimeOfOrder: Int): LiveData<MutableList<CartItemEntity>>


}