package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountShiftEntity
import com.example.restaurantpos.db.entity.ShiftEntity

@Dao
interface ShiftDAO {

    /** ShiftEntity */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShift(shift: ShiftEntity): Long

    @Query("SELECT * from shift WHERE shift_id = :shift_id")
    fun getTShiftById(shift_id: String): LiveData<ShiftEntity>

    @Query("SELECT * from shift")
    fun getListShift(): LiveData<MutableList<ShiftEntity>>


    /** AccountShiftEntity */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccountShift(accountShift: AccountShiftEntity): Long

    @Delete
    fun deleteAccountShift(accountShift: AccountShiftEntity): Long

    // Mình đang lọc theo tháng của năm nào đấy
    // Bản thân id này có thể là năm-tháng-ngày-ca
    // Lựa lựa ra rồi dùng thôi
    @Query("SELECT * from account_shift WHERE shift_id = :shift_id")
    fun getListAccountShift(shift_id: String): LiveData<MutableList<AccountShiftEntity>>

}