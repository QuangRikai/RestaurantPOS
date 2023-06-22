package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table")
data class TableEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "table_id")
    val table_id: Int,

    @ColumnInfo(name = "table_name")
    val table_name: String,

    @ColumnInfo(name = "status")
    val status: Int

    /*
    2. Unable, Problem
    1. Used
    0. Empty
     */

):Parcelable {

}