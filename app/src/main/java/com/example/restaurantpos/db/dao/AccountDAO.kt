package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountEntity

@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(account: AccountEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListAccount(listAccount: List<AccountEntity>): List<Long>

    @Delete
    fun deleteAccount(account: AccountEntity): Int

    @Query("SELECT * from account WHERE account_id = :account_id")
    fun getAccountById(account_id: Int): AccountEntity

    @Query("SELECT * from account WHERE user_name = :user_name AND password = :password")
    fun checkLogin(user_name: String, password: String): MutableList<AccountEntity>

    @Query("SELECT * from account WHERE role != 0")
    fun getAllUser(): LiveData<MutableList<AccountEntity>>

    @Query("SELECT * from account")
    fun getAllAccount(): LiveData<MutableList<AccountEntity>>

    @Query("SELECT * from account WHERE role != 0 AND account_status = 1")
    fun getAllUserActive(): LiveData<MutableList<AccountEntity>>

    @Query("SELECT * from account WHERE account_name LIKE :staffName")
    fun getStaffByName(staffName: String): LiveData<MutableList<AccountEntity>>



}