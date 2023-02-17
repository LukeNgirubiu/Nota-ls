package com.varietapp.NotaLs.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.varietapp.NotaLs.data.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event:Event)
    @Delete
    suspend fun deleteEvent(event: Event)
    @Query("SELECT * FROM event WHERE id=:id")
    suspend fun getEventById(id:Int):Event?
    @Query("SELECT * FROM event ORDER BY Date DESC")
    fun getAllEvents():Flow<List<Event>>
    @Query("SELECT COUNT(*) FROM event WHERE Date==:str")
    suspend fun countEvents(str:String):Int?
    @Query("SELECT COUNT(*) FROM event WHERE Date>:str")
    suspend fun futureEvents(str:String):Int?
}