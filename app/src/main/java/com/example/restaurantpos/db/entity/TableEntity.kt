package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table")
data class TableEntity constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "table_id")
    val table_id: Int,

    @ColumnInfo(name = "table_name")
    val table_name: String,

    @ColumnInfo(name = "table_status")
    var table_status: Int

    /*
    0. Empty
    1. New Order
    2. Old Order
    3. Problem
     */

) : Parcelable {
    companion object {
        fun toTableEntity(json: String): TableEntity? {
            return Gson().fromJson(json, TableEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}