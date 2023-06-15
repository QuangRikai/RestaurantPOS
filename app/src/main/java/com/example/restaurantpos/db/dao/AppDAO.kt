package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountEntity

@Dao
interface AppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(account: AccountEntity): Long


    @Query("SELECT * from account WHERE role != 0")
    fun getAllUser(): LiveData<MutableList<AccountEntity>>

}