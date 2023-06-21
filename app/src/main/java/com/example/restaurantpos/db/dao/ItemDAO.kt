package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.TableEntity

@Dao
interface ItemDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategoryItem(data: ItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCategoryItem(listData: List<ItemEntity>): List<Long>

    @Query("SELECT * from `item` WHERE category_id = :id")
    fun getListCategoryComponentItem(id: Int): LiveData<MutableList<ItemEntity>>

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTable(table: TableEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListTable(listTable: List<TableEntity>): List<Long>

    @Delete
    fun deleteTable(table: TableEntity): Int

    @Query("SELECT * from `table`")
    fun getAllTable(): MutableList<TableEntity>

    @Query("SELECT * from `table` WHERE table_id = :id")
    fun getTableById(id: Int): TableEntity*/














}