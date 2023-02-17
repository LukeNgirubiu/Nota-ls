package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.data.Event

sealed class PrepEvent {
    object onAddPrep:PrepEvent()
    data class onEditPrep(val id:Int):PrepEvent()
    data class onActivities(val id:Int,val date:String):PrepEvent()
    object onDeletePrep:PrepEvent()
    data class onDeleteDialog(val prep:Event):PrepEvent()
    data class onInsertPrep(val prep:Event):PrepEvent()
    object backToPreps:PrepEvent()
    object backToHome:PrepEvent()
    //(val item:Event)
}