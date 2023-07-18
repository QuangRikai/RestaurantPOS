package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "coupon")
data class CouponEntity constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coupon_id")
    val coupon_id: Int,

    @ColumnInfo(name = "coupon_code")
    var coupon_code: String,

    @ColumnInfo(name = "coupon_percent")
    var coupon_percent: Int


) : Parcelable {
    companion object {
        fun toCoupon(json: String): CouponEntity? {
            return Gson().fromJson(json, CouponEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}