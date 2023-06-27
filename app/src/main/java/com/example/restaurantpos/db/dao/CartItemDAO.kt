package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.CartItemEntity

@Dao
interface CartItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCartItem(data: CartItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCartItem(data: ArrayList<CartItemEntity>): List<Long>

    @Delete
    fun deleteCartItem(data: CartItemEntity): Int

    // Get ListOrder Of OrderedTable, By order_id (Bill)
    @Query("SELECT * FROM cart_item WHERE order_id = :order_id")
    fun getListCartItem(order_id: String): LiveData<MutableList<CartItemEntity>>

    @Query("SELECT * FROM cart_item WHERE order_id = :order_id AND cart_item_status = 0")
    fun getListCartItemV0(order_id: String): LiveData<MutableList<CartItemEntity>>

    @Query(
        "SELECT * FROM cart_item WHERE cart_item_status < 3  \n" +
                "ORDER BY \n" +
                "CASE WHEN :sortTime = 0 THEN order_id END ASC, \n" +
                "CASE WHEN :sortTime = 1 THEN order_id END DESC"
    )
    fun getListCartItemOfKit(sortTime: Int): LiveData<MutableList<CartItemEntity>>


}