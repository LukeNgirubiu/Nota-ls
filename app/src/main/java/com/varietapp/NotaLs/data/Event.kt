package com.varietapp.NotaLs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey val id:Int?=null,
    val name:String?,
    val description:String,
    val Date:String,
    val activaties:Int
)
