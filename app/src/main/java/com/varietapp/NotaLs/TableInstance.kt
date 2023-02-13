package com.varietapp.NotaLs

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varietapp.NotaLs.dao.*
import com.varietapp.NotaLs.data.*

@Database(
    entities = [Event::class,EventActivity::class,Shopping::class,Items::class,Service::class],
    version=1
)
abstract class TableInstance:RoomDatabase() {
    abstract val eventDao:EventDao
    abstract val eventActivityDao:EventActivityDao
    abstract val shoppingDao: ShoppingDao
    abstract val itemsDao: ItemsDao
    abstract val serviceDao:ServicesDao
}