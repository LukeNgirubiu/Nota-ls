package com.varietapp.NotaLs.Views
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.Utils.ItemsEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.data.Items
import com.varietapp.NotaLs.data.Shopping
import com.varietapp.NotaLs.repository.ItemsRepo
import com.varietapp.NotaLs.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val repository:ItemsRepo,
    private val repoShopping: ShoppingRepository
):ViewModel() {
    val eventSender= Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    fun getAllItems(shoppId:Int): Flow<List<Items>> {
        return repository.getItemsByShopId(shoppId)
    }
    var isDialogOn by mutableStateOf(false)
        private set
    var shoppingId by mutableStateOf<Int?>(null)
        private set
    var dialogueType by mutableStateOf(1)
        private set
    var currItem by mutableStateOf<Items?>(null)

    var shoping by mutableStateOf<Shopping?>(null)

   suspend fun getShop(shoppId:Int):Shopping?{
        return repoShopping.getShoppingById(shoppId)
    }
    fun onEvent(event:ItemsEvent){
        when(event){
            is ItemsEvent.openDialog->{
                isDialogOn=true
                dialogueType=event.showType//Update or insert
                shoppingId=event.shoppId
                if(event.showType==2){
                    currItem =event.item
                    println("Hitting here at 43")
                }
            }
            is ItemsEvent.closeDialog->{
                isDialogOn=false
                println("Shopping id $shoppingId")
            }
            is ItemsEvent.onSave->{
                viewModelScope.launch {
                       currItem=if(dialogueType==1){
                           Items(name=event.item, quantity = event.quantity, cost = event.totalAmount, shoppId = shoppingId!!)
                       }
                       else{
                           Items(id=currItem!!.id,name=event.item, quantity = event.quantity,cost = event.totalAmount,shoppId = shoppingId!!)
                       }
                       repository.insertItem(currItem!!)
                    if (dialogueType==2){
                        sendEvent(UiEvents.ShowSnackBar(
                            message = "Updated an item",
                            action = "",
                        ))
                    }
                    isDialogOn=false
                }

            }
            is ItemsEvent.onTickItem->{
             viewModelScope.launch {
                 currItem=event.item
                 repository.insertItem(event.item!!.copy(ticked = true))
                 sendEvent(UiEvents.ShowSnackBar(
                     message = "Ticked on ${currItem!!.name}",
                     action = "Undo",
                     1
                 ))

             }
            }
            is ItemsEvent.onUndoAction->{
                viewModelScope.launch {
                    repository.insertItem(currItem!!)
                }
            }
            is ItemsEvent.onDelete->{
                viewModelScope.launch {
                    currItem=event.item
                    repository.deleteItem(currItem!!)
                    sendEvent(UiEvents.ShowSnackBar(
                        message = "Deleted ${currItem!!.name}",
                        action = "Undo",
                        1
                    ))
                }
            }
            is ItemsEvent.onToShop->{
                sendEvent(UiEvents.Navigate("shopping"))
            }
        }
    }

    private fun sendEvent(event:UiEvents){
        viewModelScope.launch{
            eventSender.send(event)
        }
    }


}