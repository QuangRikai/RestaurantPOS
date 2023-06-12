package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "orderheader")
data class OrderHeaderEntity(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "order_header_id")
    val order_header_id: Int,

    @ColumnInfo(name = "order_quantity")
    val order_quantity: Int


): Parcelable {
}