package com.example.restaurantpos.db.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.restaurantpos.db.dao.AccountDAO
import com.example.restaurantpos.db.dao.AppDAO
import com.example.restaurantpos.db.dao.CartDAO
import com.example.restaurantpos.db.dao.CategoryDAO
import com.example.restaurantpos.db.dao.ItemDAO
import com.example.restaurantpos.db.dao.TableDAO
import com.example.restaurantpos.db.entity.AccountEntity
import com.example.restaurantpos.db.entity.AccountShiftEntity
import com.example.restaurantpos.db.entity.CategoryEntity
import com.example.restaurantpos.db.entity.CustomerEntity
import com.example.restaurantpos.db.entity.ItemEntity
import com.example.restaurantpos.db.entity.CartEntity
import com.example.restaurantpos.db.entity.CustomerOrderEntity
import com.example.restaurantpos.db.entity.OrderEntity
import com.example.restaurantpos.db.entity.ShiftEntity
import com.example.restaurantpos.db.entity.TableEntity

@Database(
    version = 1,
    entities = [
        AccountEntity::class,
        AccountShiftEntity::class,
        CartEntity::class,
        CategoryEntity::class,
        CustomerEntity::class,
        CustomerOrderEntity::class,
        ItemEntity::class,
        OrderEntity::class,
        ShiftEntity::class,
        TableEntity::class
    ]
)
public abstract class PosRoomDatabase: RoomDatabase (){

    abstract fun appDAO(): AppDAO
    abstract fun accountDAO(): AccountDAO
    abstract fun categoryDAO(): CategoryDAO
    abstract fun itemDAO(): ItemDAO
    abstract fun tableDAO(): TableDAO
    abstract fun cartDAO(): CartDAO

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