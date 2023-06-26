package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart")
data class CartEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "cart_id")
    val cart_id: Int,

    @ColumnInfo(name = "item_id")
    val item_id: Int,

    @ColumnInfo(name = "order_id")
    val order_id: Int,
    
    @ColumnInfo(name = "order_quantity")
    val order_quantity: Int,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "cart_status")
    val cart_status: Int

    /*
0: Manager
1: Receptionist
2: Kitchen
 */

): Parcelable {

}