package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.dao.EventDao
import com.varietapp.NotaLs.data.Event
import kotlinx.coroutines.flow.Flow

class EventRepositoryImplement(private val dao:EventDao):EventRepository {
    override suspend fun insertEvent(event: Event) {
        dao.insertEvent(event)
    }
    override suspend fun deleteEvent(event: Event) {
        dao.deleteEvent(event)
    }
    override suspend fun getEventById(id: Int): Event? {
        return dao.getEventById(id)
    }
    override fun getEvents(): Flow<List<Event>> {
        return dao.getAllEvents()
    }

    override suspend fun countEvents(str: String): Int? {
        return dao.countEvents(str)
    }

    override suspend fun futureEvents(str: String): Int? {
        return dao.futureEvents(str)
    }
}