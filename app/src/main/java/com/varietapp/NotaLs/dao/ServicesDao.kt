package com.varietapp.NotaLs.dao

import androidx.room.*
import com.varietapp.NotaLs.data.Service
import kotlinx.coroutines.flow.Flow
@Dao
interface ServicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service:Service)
    @Delete
    suspend fun deleteService(service:Service)
    @Query("SELECT * FROM service WHERE id=:id")
    suspend fun getServiceById(id:Int): Service?
    @Query("SELECT * FROM service")
    fun getAllIServices(): Flow<List<Service>>
    @Query("SELECT COUNT(*) FROM service WHERE Date==:str")
    suspend fun countToday(str:String): Int?
    @Query("SELECT COUNT(*) FROM service WHERE Date<:str")
    suspend fun countPast(str:String): Int?
}