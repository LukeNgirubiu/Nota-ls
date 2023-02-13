package com.varietapp.NotaLs.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varietapp.NotaLs.dao.ServicesDao
import com.varietapp.NotaLs.data.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    suspend fun insertService(service: Service)
    suspend fun deleteService(service: Service)
    suspend fun getServiceById(id:Int): Service?
    fun getAllIServices(): Flow<List<Service>>
}