package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.TableEntity

@Dao
interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrder(data : OrderEntity) : Long

    @Delete
    fun deleteOrder(data: OrderEntity): Int

    // Get 1 order với order_id
    @Query("SELECT * FROM `order` WHERE order_id = :order_id")
    fun getOrder(order_id : String) : LiveData<OrderEntity>

    @Query("SELECT * FROM `order` WHERE table_id = :table_id ORDER BY order_create_time DESC")
    fun getOrderByTable(table_id : Int) : LiveData<OrderEntity>

    @Query("SELECT * FROM `order` WHERE customer_id = :id ORDER BY order_create_time DESC")
    fun getListOrderOfCustomer(id : Int) : LiveData<MutableList<OrderEntity>>


}