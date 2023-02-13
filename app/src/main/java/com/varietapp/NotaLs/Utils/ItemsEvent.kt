package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.data.Items

sealed class ItemsEvent{
    data class openDialog(val shoppId:Int,val showType:Int=1,val item:Items?=null):ItemsEvent()
    object closeDialog:ItemsEvent()
    data class onSave(val item:String,val totalAmount:Double,val quantity:Int):ItemsEvent()
    data class onTickItem(val item: Items?):ItemsEvent()
    object onUndoAction:ItemsEvent()
    data class onDelete(val item: Items?):ItemsEvent()
    object onToShop:ItemsEvent()
}
