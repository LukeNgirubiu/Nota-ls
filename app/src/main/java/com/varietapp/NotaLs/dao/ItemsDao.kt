package com.varietapp.NotaLs.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.varietapp.NotaLs.data.Items
import kotlinx.coroutines.flow.Flow
@Dao
interface ItemsDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item:Items)
    @Delete
    suspend fun deleteItem(item:Items)
    @Query("SELECT * FROM items WHERE id=:id")
    suspend fun getItemsById(id:Int): Items?
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Items>>
    @Transaction
    @Query("SELECT * FROM items WHERE shoppId=:shopId")
    fun getItemsByShopId(shopId:Int): Flow<List<Items>>
}