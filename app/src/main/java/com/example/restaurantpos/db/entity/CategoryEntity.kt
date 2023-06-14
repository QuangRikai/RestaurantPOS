package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "category")
data class CategoryEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "category_id")
    val category_id: Int,

    @ColumnInfo(name = "category_name")
    val category_name: String

): Parcelable {
}