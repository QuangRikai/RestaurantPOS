package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CartItemEntity
import com.example.restaurantpos.db.entity.CartItemStatusEntity
import com.example.restaurantpos.db.entity.TableStatusEntity

@Dao
interface CartItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCartItem(data: CartItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCartItem(data: List<CartItemEntity>): List<Long>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCartItemStatus(listCartItemStatus: List<CartItemStatusEntity>): List<Long>

    @Delete
    fun deleteCartItem(data: CartItemEntity): Int

    // Get ListOrder Of OrderedTable, By order_id (Bill)

    @Query("SELECT * FROM `order`  JOIN cart_item  ON (`order`.order_id = cart_item .order_id) JOIN `table`  ON (`order`.table_id = `table`.table_id) WHERE `table`.table_id = :tableId AND `order`.order_status_id = 1")
    fun getListCartItemByTableIdAndOrderStatus(tableId: Int): LiveData<MutableList<CartItemEntity>>

    @Query("SELECT * FROM cart_item WHERE order_id = :order_id")
    fun getListCartItemByOrderId(order_id: String): LiveData<MutableList<CartItemEntity>>

//    cart_item_status = 0: Những thứ vẫn chưa làm thì cho phép Edit/Delete
    @Query("SELECT * FROM cart_item WHERE order_id = :order_id AND cart_item_status_id = 0")
    fun getListCartItemOnWaiting(order_id: String): LiveData<MutableList<CartItemEntity>>

    @Query("SELECT * FROM cart_item WHERE cart_item_status_id < 4")
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
        "SELECT * FROM cart_item WHERE cart_item_status_id < 2  \n" +
                "ORDER BY \n" +
                "CASE WHEN :sortByTimeOfOrder = 0 THEN order_id END DESC, \n" +
                "CASE WHEN :sortByTimeOfOrder = 1 THEN order_id END ASC"
    )
    fun getListCartItemOfKitchenBySortTime(sortByTimeOfOrder: Int): LiveData<MutableList<CartItemEntity>>

    @Query("SELECT * FROM `order`  JOIN cart_item  ON (`order`.order_id = cart_item .order_id) JOIN `table`  ON (`order`.table_id = `table`.table_id) WHERE `table`.table_id = :tableId")
    fun getListCartItemByTableId(tableId: Int): LiveData<MutableList<CartItemEntity>>


    @Query("SELECT SUM(cart_item.order_quantity * item.price) FROM cart_item JOIN `order` ON `order`.order_id = cart_item.order_id JOIN item ON cart_item.item_id = item.item_id   WHERE `order`.order_id = :orderID AND `order`.order_status_id = 1")
    fun getSubTotal(orderID: String): Float

    /** Revenue */
    @Query("SELECT SUM(cart_item.order_quantity * item.price) FROM `order` JOIN cart_item ON `order`.order_id = cart_item.order_id JOIN item ON cart_item.item_id = item.item_id   WHERE `order`.order_create_time LIKE :time")
    fun getRevenueOfDay(time: String): Float


    @Query("SELECT SUM(cart_item.order_quantity * item.price) FROM `order` Join cart_item ON `order`.order_id = cart_item.order_id JOIN item ON cart_item.item_id = item.item_id  WHERE cart_item.item_id = :id_item AND `order`.order_create_time LIKE :time")
    fun getRevenueOfDayOfItem(id_item: Int, time: String): Float

}