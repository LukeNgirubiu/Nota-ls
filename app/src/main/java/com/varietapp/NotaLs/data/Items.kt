package com.varietapp.NotaLs.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Shopping::class,
            childColumns = ["shoppId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Items(
    @PrimaryKey
    val id:Int?=null,
    val name:String,
    val cost:Double,
    val quantity:Int,
    val shoppId:Int,
    val ticked:Boolean=false
)
/* */