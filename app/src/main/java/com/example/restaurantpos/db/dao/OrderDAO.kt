package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountStatusEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.OrderStatusEntity
import com.example.restaurantpos.db.entity.TableEntity

@Dao
interface OrderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrder(data : OrderEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListOrderStatus(listOrderStatus: List<OrderStatusEntity>): List<Long>
    @Delete
    fun deleteOrder(data: OrderEntity): Int

    // Get 1 order với order_id
    @Query("SELECT * FROM `order` WHERE order_id = :order_id")
    fun getOrder(order_id : String) : LiveData<OrderEntity>

    // Câu chuyện ORDER BY order_create_time DESC --> 13-51:00   Cần xem lại???
    @Query("SELECT * FROM `order` WHERE table_id = :table_id ORDER BY order_create_time DESC")
    fun getOrderByTable(table_id : Int) : LiveData<OrderEntity>

    @Query("SELECT * FROM `order` WHERE customer_id = :customerId ORDER BY order_create_time DESC")
    fun getListOrderByCustomerId(customerId : Int) : LiveData<MutableList<OrderEntity>>


}