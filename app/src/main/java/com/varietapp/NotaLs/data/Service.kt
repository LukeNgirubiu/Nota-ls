package com.varietapp.NotaLs.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Service(
    @PrimaryKey
    val id:Int?=null,
    val name:String,
    val cost:Double,
    val checked:Boolean=false,
    val Date:String?=null

)
