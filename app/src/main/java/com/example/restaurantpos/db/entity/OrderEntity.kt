package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "order")
data class OrderEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "order_id")
    val order_id: Int,

    @ColumnInfo(name = "created_by_account_id")
    val created_by_account_id: String,

    @ColumnInfo(name = "order_create_time")
    val order_create_time: String,

    @ColumnInfo(name = "paid_time")
    val paid_time: String,

    @ColumnInfo(name = "payment_amount")
    val payment_amount: String,

    @ColumnInfo(name = "order_status")
    val order_status: Int

): Parcelable {
}