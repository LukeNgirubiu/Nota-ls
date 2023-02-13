package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.data.EventActivity

sealed class ActivityEvent{
    data class onInsert(val activity:EventActivity,val checking:Int?=null):ActivityEvent()
    data class onEdit(val id:Int):ActivityEvent()
    data class onDelete(val id:Int):ActivityEvent()
    object onBackPrepare:ActivityEvent()
    object undoStatus:ActivityEvent()
    object undoDelete:ActivityEvent()
}
