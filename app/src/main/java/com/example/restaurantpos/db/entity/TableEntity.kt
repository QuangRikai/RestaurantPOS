package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table")
data class TableEntity (

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "table_id")
    val table_id: Int,

    @ColumnInfo(name = "table_name")
    val table_name: String

):Parcelable {

}