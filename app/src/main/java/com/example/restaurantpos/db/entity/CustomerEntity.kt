package com.example.restaurantpos.db.entity


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Date

@Parcelize
@Entity(tableName = "customer")
data class CustomerEntity constructor(

    @PrimaryKey(autoGenerate= true)
    @ColumnInfo(name = "category_id")
    val customer_id: Int,

    @ColumnInfo(name = "customer_name")
    val customer_name: Int,

    @ColumnInfo(name = "phone")
    val phone: Int,

    @ColumnInfo(name = "birthday")
    val birthday: String,

    @ColumnInfo(name = "created_by_account_id")
    val created_by_account_id: Int

/*    @ForeignKey (
        entity = AccountEntity::class,
        parentColumns = ["account_id"],
        childColumns = ["created_by_account_id"],
        onDelete = NO_ACTION,
        onUpdate = NO_ACTION,
        deferred = false
    )
    @ColumnInfo(name = "created_by_account_id")
    val created_by_account_id: String*/

): Parcelable {
}