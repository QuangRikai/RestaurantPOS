package com.example.restaurantpos.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.restaurantpos.db.entity.AccountEntity

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(account: AccountEntity): Long


}