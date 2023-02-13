package com.varietapp.NotaLs.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varietapp.NotaLs.data.Items
import kotlinx.coroutines.flow.Flow

interface ItemsRepo {
    suspend fun insertItem(item: Items)
    suspend fun deleteItem(item: Items)
    suspend fun getItemsById(id:Int): Items?
    fun getAllItems(): Flow<List<Items>>
    fun getItemsByShopId(shopId:Int): Flow<List<Items>>
}