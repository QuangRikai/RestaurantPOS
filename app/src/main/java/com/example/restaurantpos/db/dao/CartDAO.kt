package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.CartEntity

@Dao
interface CartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCart(data: CartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCart(data: ArrayList<CartEntity>): List<Long>

    @Delete
    fun deleteCart(data: CartEntity): Int

    /** Get ListOrder Of OrderedTable */
    @Query("SELECT * FROM cart WHERE order_id = :order_id")
    fun getListCart(order_id: String): LiveData<MutableList<CartEntity>>

    @Query("SELECT * FROM cart WHERE order_id = :order_id AND cart_status = 0")
    fun getListCartV0(order_id: String): LiveData<MutableList<CartEntity>>

    @Query(
        "SELECT * FROM cart WHERE cart_status < 3  \n" +
                "ORDER BY \n" +
                "CASE WHEN :sortTime = 0 THEN order_id END ASC, \n" +
                "CASE WHEN :sortTime = 1 THEN order_id END DESC"
    )
    fun getListCartOfKit(sortTime: Int): LiveData<MutableList<CartEntity>>


}