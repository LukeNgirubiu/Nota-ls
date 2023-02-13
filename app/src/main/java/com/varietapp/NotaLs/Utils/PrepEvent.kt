package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.data.Event

sealed class PrepEvent {
    object onAddPrep:PrepEvent()
    data class onEditPrep(val id:Int):PrepEvent()
    data class onActivities(val id:Int,val date:String):PrepEvent()
    data class onDeletePrep(val item:Event):PrepEvent()
    object undoDelete:PrepEvent()
    data class onInsertPrep(val prep:Event):PrepEvent()
    object backToPreps:PrepEvent()
    object backToHome:PrepEvent()
}