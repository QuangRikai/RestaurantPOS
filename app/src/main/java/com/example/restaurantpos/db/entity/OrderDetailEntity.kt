package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "order_detail")
data class OrderDetailEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "order_detail_id")
    val order_detail_id: Int,
    
    @ColumnInfo(name = "order_quantity")
    val order_quantity: Int


): Parcelable {
}