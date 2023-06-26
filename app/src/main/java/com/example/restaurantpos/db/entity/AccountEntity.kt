package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class AccountEntity constructor(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    val account_id: Int,

    @ColumnInfo(name = "account_name")
    val account_name: String,

    @ColumnInfo(name = "user_name")
    val user_name: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "role")
    val role: Int,

    @ColumnInfo(name = "account_status")
    val account_status: Boolean


    /*
    0: Manager
    1: Receptionist
    2: Kitchen
     */

) : Parcelable {
    fun toAccount(json: String): AccountEntity? {
        return Gson().fromJson(json, AccountEntity::class.java)
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}