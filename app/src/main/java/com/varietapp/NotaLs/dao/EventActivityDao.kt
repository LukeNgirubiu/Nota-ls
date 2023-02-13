package com.varietapp.NotaLs.dao

import androidx.room.*
import com.varietapp.NotaLs.data.Event
import com.varietapp.NotaLs.data.EventActivity
import kotlinx.coroutines.flow.Flow
@Dao
interface EventActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventActivity(event: EventActivity)
    @Delete
    suspend fun deleteEventActivity(event: EventActivity)
    @Query("SELECT * FROM EventActivity WHERE id=:id")
    suspend fun getEventActivityById(id:Int): EventActivity?
    @Query("SELECT * FROM EventActivity WHERE eventId=:eventId")
    fun getAllEventsActivity(eventId:Int): Flow<List<EventActivity>>
}