package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class BillEntity constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bill_id")
    val bill_id: Int,

    @ColumnInfo(name = "bill_date")
    var bill_date: String,

    @ColumnInfo(name = "bill_table")
    var bill_table: String,

    @ColumnInfo(name = "bill_customer")
    var bill_customer: String,

    @ColumnInfo(name = "bill_staff")
    var bill_staff: String,

    @ColumnInfo(name = "bill_subTotal")
    var bill_subTotal: String,

    @ColumnInfo(name = "bill_coupon")
    val bill_coupon: Int,

    @ColumnInfo(name = "account_status_id")
    var account_status_id: Boolean


    /*
    0: Manager
    1: Receptionist
    2: Kitchen
     */

) : Parcelable {
    companion object {
        fun toAccount(json: String): BillEntity? {
            return Gson().fromJson(json, BillEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}