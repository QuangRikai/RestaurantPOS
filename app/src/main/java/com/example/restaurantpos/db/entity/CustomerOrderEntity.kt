package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "customer_order")
data class CustomerOrderEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "customer_order_id")
    val customer_order_id: Int,

    @ColumnInfo(name = "customer_id")
    val customer_id: Int,

    @ColumnInfo(name = "table_id")
    val table_id: Int,

    @ColumnInfo(name = "order_header_id")
    val order_header_id: Int

):Parcelable {

}