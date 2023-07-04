package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shift")
data class ShiftEntity constructor(
    // Ý tưởng
    /*
    Kết hợp yyyy/MM/dd shift_name  --> Không thằng nào giống thằng nào
    Sau này filter cũng dễ

    Chốt: shift_id dạng:    yyyy/MM/dd_shift_name
    */
    @PrimaryKey
    @ColumnInfo(name = "shift_id")
    val shift_id: String,

    @ColumnInfo(name = "shift_time")
    val shift_time: String,

    @ColumnInfo(name = "shift_name")
    val shift_name: Int
    /*
    1. Morning
    2. Afternoon
    3. Night
    Đã thế này thì cần mẹ gì đến shift_time. shift_time  TRÙNG shift_name???
     */

):Parcelable {
    companion object {
        fun toShiftObject(json: String): ShiftEntity? {
            return Gson().fromJson(json, ShiftEntity::class.java)
        }
    }


    fun toJson(): String {
        return Gson().toJson(this)
    }

}