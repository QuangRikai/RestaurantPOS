package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "item")
data class ItemEntity(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "item_id")
    val item_id: Int,

    @ColumnInfo(name = "item_name")
    val item_name: String,

    @ColumnInfo(name = "price")
    val price: Float,

    @ColumnInfo(name = "image")
    val image: Int,

    @ColumnInfo(name = "inventory_quantity")
    val inventory_quantity: Int

): Parcelable {
}