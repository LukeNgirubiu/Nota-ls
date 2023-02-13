package com.varietapp.NotaLs.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varietapp.NotaLs.data.Shopping
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    suspend fun insertShopping(shopping: Shopping)
    suspend fun deleteShopping(shopping: Shopping)
    suspend fun getShoppingById(id:Int): Shopping?
    fun getAllShopping(): Flow<List<Shopping>>
}