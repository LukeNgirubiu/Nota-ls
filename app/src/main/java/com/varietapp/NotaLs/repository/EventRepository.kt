package com.varietapp.NotaLs.repository

import com.varietapp.NotaLs.data.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun insertEvent(event:Event)
    suspend fun deleteEvent(event: Event)
    suspend fun getEventById(id:Int): Event?
    fun getEvents():Flow<List<Event>>
    suspend fun countEvents(str:String):Int?
    suspend fun futureEvents(str:String):Int?
}