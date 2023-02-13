package com.varietapp.NotaLs.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "EventActivity",
    foreignKeys = [
    ForeignKey(
    entity = Event::class,
    childColumns = ["eventId"],
    parentColumns = ["id"],
    onDelete = CASCADE
)])
data class EventActivity(
    @PrimaryKey
    val id:Int?=null,
    val name:String,
    val time:String,
    val eventId:Int,
    val done:Boolean=false
)
