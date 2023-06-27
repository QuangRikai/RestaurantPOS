package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// Quản lý Các Order (Bill)
@Parcelize
@Entity(tableName = "order")
data class OrderEntity constructor(

    // Không tự động tăng, dùng 1 cách ngẫu nhiên sinh ra tại thời điểm đấy. By Time
    @PrimaryKey
    @ColumnInfo(name = "order_id")
    val order_id: String,

    @ColumnInfo(name = "customer_id")
    val customer_id: Int,

    @ColumnInfo(name = "table_id")
    val table_id: Int,

    @ColumnInfo(name = "created_by_account_id")
    val created_by_account_id: Int,

    @ColumnInfo(name = "order_create_time")
    val order_create_time: String,

    @ColumnInfo(name = "paid_time")
    val paid_time: String,

    @ColumnInfo(name = "payment_amount")
    val payment_amount: Float,

    @ColumnInfo(name = "order_status")
    val order_status: Int

    /*
    0. Đang trong quá trình tạo Bill (Mới click cái bàn xong)
    1. Chốt Bill nhưng chưa thanh toán
    2. Thanh toán rồi
    */

): Parcelable {
}