package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.data.Service
sealed class ServiceEvent{
    data class onAddService(val id:Int):ServiceEvent()
    object onHome:ServiceEvent()
    data class onSave(val service:Service):ServiceEvent()
    data class onUpdate(val service: Service):ServiceEvent()
    data class onCheck(val service: Service):ServiceEvent()
    data class onDelete(val service: Service):ServiceEvent()
    object onUndoAction:ServiceEvent()
}
