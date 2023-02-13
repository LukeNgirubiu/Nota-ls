package com.varietapp.NotaLs.Utils

import com.varietapp.NotaLs.PREP_ID
import com.varietapp.NotaLs.Routes
import com.varietapp.NotaLs.data.Shopping

sealed class ShoppingEvent{
object onCloseDialog:ShoppingEvent()
data class  onOpenDialog(val type:Int):ShoppingEvent()//Type 1 is create 2 is update
data class onSave(val shopping: Shopping):ShoppingEvent()
data class onOpenDialogUpdate(val shopping: Shopping):ShoppingEvent()
data class onDelete(val shopping: Shopping):ShoppingEvent()
object undoDelete:ShoppingEvent()
data class onToItems(val shopId:Int):ShoppingEvent()
object onBackHome:ShoppingEvent()
}
