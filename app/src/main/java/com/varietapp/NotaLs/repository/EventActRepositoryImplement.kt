package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.dao.EventActivityDao
import com.varietapp.NotaLs.data.Event
import com.varietapp.NotaLs.data.EventActivity
import kotlinx.coroutines.flow.Flow

class EventActRepositoryImplement(private val eventActDao:EventActivityDao):EventActRepository {
    override suspend fun insertEventActivity(event: EventActivity) {
        eventActDao.insertEventActivity(event)
    }

    override suspend fun deleteEventActivity(event: EventActivity) {
        eventActDao.deleteEventActivity(event)
    }

    override suspend fun getEventActivityById(id: Int): EventActivity? {
        return eventActDao.getEventActivityById(id)
    }

    override fun getAllEventsActivity(eventId:Int): Flow<List<EventActivity>> {
        return eventActDao.getAllEventsActivity(eventId = eventId)
    }

}