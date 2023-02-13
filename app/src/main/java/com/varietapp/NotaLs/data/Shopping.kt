package com.varietapp.NotaLs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shopping(
    @PrimaryKey
    val id:Int?=null,
    val title:String,
    val description:String,
    val dateDue:String,
)
