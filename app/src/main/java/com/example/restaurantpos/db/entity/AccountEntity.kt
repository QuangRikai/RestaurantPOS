package com.example.restaurantpos.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "account")
data class AccountEntity (

        @PrimaryKey(autoGenerate= true)
        @ColumnInfo(name = "account_id")
        val account_id: Int,

        @ColumnInfo(name = "account_name")
        val account_name: String,

        @ColumnInfo(name = "user_name")
        val user_name: String,

        @ColumnInfo(name = "password")
        val password: String,

        @ColumnInfo(name = "role")
        val role: Int
        /*
        1: Manager
        2: Receptionist
        3: Kitchen
         */

): Parcelable {
        fun toAccount(json : String) : AccountEntity? {
                return Gson().fromJson(json, AccountEntity::class.java)
        }

        fun toJson() : String {
                return Gson().toJson(this)
        }
}