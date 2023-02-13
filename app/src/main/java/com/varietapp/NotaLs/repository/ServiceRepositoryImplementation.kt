package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.dao.ServicesDao
import com.varietapp.NotaLs.data.Service
import kotlinx.coroutines.flow.Flow

class ServiceRepositoryImplementation(private val dao:ServicesDao):ServiceRepository {
    override suspend fun insertService(service: Service) {
        dao.insertService(service)
    }

    override suspend fun deleteService(service: Service) {
        dao.deleteService(service)
    }

    override suspend fun getServiceById(id: Int): Service? {
        return dao.getServiceById(id)
    }

    override fun getAllIServices(): Flow<List<Service>> {
       return dao.getAllIServices()
    }
}