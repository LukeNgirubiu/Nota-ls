package com.varietapp.NotaLs.dao

import androidx.room.*
import com.varietapp.NotaLs.data.Shopping
import kotlinx.coroutines.flow.Flow
@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertShopping(shopping: Shopping)
    @Delete
    suspend fun deleteShopping(shopping: Shopping)
    @Query("SELECT * FROM shopping WHERE id=:id")
    suspend fun getShoppingById(id:Int): Shopping?
    @Query("SELECT * FROM shopping")
    fun getAllShopping(): Flow<List<Shopping>>
    @Query("SELECT COUNT(*) FROM shopping WHERE dateDue==:query")
    suspend fun countShopping(query:String): Int?
    @Query("SELECT COUNT(*) FROM shopping WHERE dateDue>:query")
    suspend fun futureShopping(query:String): Int?
}