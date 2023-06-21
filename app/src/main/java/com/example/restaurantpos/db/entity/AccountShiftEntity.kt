package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account_shift")
data class AccountShiftEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "account_shift_id")
    val account_shift_id: Int,

    @ColumnInfo(name = "shift_name")
    val shift_id: Int,

    @ColumnInfo(name = "account_id")
    val account_id: Int

):Parcelable {

}