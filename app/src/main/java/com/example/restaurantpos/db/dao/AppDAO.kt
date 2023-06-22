package com.example.restaurantpos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurantpos.db.entity.AccountEntity

@Dao
interface AppDAO {
/*

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAccount(account: AccountEntity): Long
*/


    /*@Query("SELECT * FROM meme")
fun getAllMeme(): MutableList<MemeEntity>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun addAllMeme(listData: ArrayList<MemeEntity>): List<Long>

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun addUser(userEntity: UserEntity): Long

@Query("delete from meme where id in (:listData)")
fun deleteAllMeme(listData: List<String>)

@Delete
fun deleteUser(userEntity: UserEntity): Int*/

}