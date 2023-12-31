package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shift")
data class ShiftEntity (

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "shift_id")
    val shift_id: Int,

    @ColumnInfo(name = "shift_name")
    val shift_name: Int

):Parcelable {

}