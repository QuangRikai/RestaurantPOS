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
    */
    @PrimaryKey
    @ColumnInfo(name = "shift_id")
    val shift_id: String,

    @ColumnInfo(name = "shift_time")
    val shift_time: String,

    @ColumnInfo(name = "shift_name")
    val shift_name: String

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