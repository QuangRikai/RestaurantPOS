package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "order_header")
data class OrderHeaderEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "order_header_id")
    val order_header_id: Int,

    @ColumnInfo(name = "customer_order_time")
    val customer_order_time: String,

    @ColumnInfo(name = "payment_amount")
    val payment_amount: String


): Parcelable {
}