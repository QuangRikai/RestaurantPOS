package com.example.restaurantpos.db.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.dao.TableDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.OrderDetailEntity
import com.example.restaurantpos.db.entity.OrderHeaderEntity
import com.example.restaurantpos.db.entity.ShiftEntity
import com.example.restaurantpos.db.entity.TableEntity

@Database(
    version = 1,
    entities = [
        AccountEntity::class,
        CategoryEntity::class,
        CustomerEntity::class,
        ItemEntity::class,
        OrderDetailEntity::class,
        OrderHeaderEntity::class,
        ShiftEntity::class,
        TableEntity::class
    ]
)
public abstract class PosRoomDatabase: RoomDatabase (){

    abstract fun appDAO(): AppDAO
    abstract fun accountDAO(): AccountDAO
    abstract fun tableDAO(): TableDAO

    companion object{
        @Volatile
        private  var INSTANCE: PosRoomDatabase? = null
        fun getInstance(context: Context): PosRoomDatabase{
            return INSTANCE?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PosRoomDatabase::class.java,
                "quangdb"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE =instance
            return instance
        }
        }
    }



}