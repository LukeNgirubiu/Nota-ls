package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.PREP_ID
import com.varietapp.NotaLs.Routes
import com.varietapp.NotaLs.data.Shopping

sealed class ShoppingEvent{
object onCloseDialog:ShoppingEvent()
object  onAdd:ShoppingEvent()//Type 1 is create 2 is update
data class onSave(val shopping: Shopping):ShoppingEvent()
data class onUpdate(val shopp_id:Int):ShoppingEvent()
object onDelete:ShoppingEvent()
data class onDeleteDialog(val shopping: Shopping):ShoppingEvent()
data class onToItems(val shopId:Int,val currency:String):ShoppingEvent()
object onBackHome:ShoppingEvent()
}
