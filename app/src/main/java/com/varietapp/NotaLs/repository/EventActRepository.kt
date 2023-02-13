package com.varietapp.NotaLs.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varietapp.NotaLs.data.EventActivity
import kotlinx.coroutines.flow.Flow

interface EventActRepository {
    suspend fun insertEventActivity(event: EventActivity)
    suspend fun deleteEventActivity(event: EventActivity)
    suspend fun getEventActivityById(id:Int): EventActivity?
    fun getAllEventsActivity(eventId:Int): Flow<List<EventActivity>>
}