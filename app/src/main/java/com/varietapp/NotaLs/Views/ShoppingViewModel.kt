package com.varietapp.NotaLs.Views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.Utils.ShoppingEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.data.EventActivity
import com.varietapp.NotaLs.data.Shopping
import com.varietapp.NotaLs.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) :ViewModel() {
    var isDialogOn by mutableStateOf(false)
        private set
    var isDeleteDialogOn by mutableStateOf(false)
        private set
    var dialogueType by mutableStateOf(1)
        private set
    var shopping by mutableStateOf<Shopping?>(null)
        private set
    val eventSender= Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    fun getAllShoppings(): Flow<List<Shopping>> {
        return repository.getAllShopping()
    }
    fun closeDelete(){
        isDeleteDialogOn=false
        shopping=null
    }
    fun onEvent(event:ShoppingEvent){
        when(event){
            is ShoppingEvent.onOpenDialog->{
                isDialogOn=true
               dialogueType=event.type
            }
            is ShoppingEvent.onCloseDialog->{
                isDialogOn=false
                dialogueType=1
            }
            is ShoppingEvent.onSave->{
                viewModelScope.launch {
                    val shop=if (dialogueType==2) event.shopping.copy(id=shopping!!.id) else event.shopping
                    repository.insertShopping(shop)
                    if (dialogueType==2){
                        sendEvent(UiEvents.ShowSnackBar("Shopping updated",""))
                    }
                    isDialogOn=false
                    dialogueType=1
                }
            }
            is ShoppingEvent.onOpenDialogUpdate->{
                isDialogOn=true
                dialogueType=2
                shopping=event.shopping
            }
            is ShoppingEvent.onDeleteDialog->{
                shopping=event.shopping
                isDeleteDialogOn=true
            }
            is ShoppingEvent.onDelete->{
                viewModelScope.launch {
                    repository.deleteShopping(shopping!!)
                    closeDelete()
                  //  sendEvent(UiEvents.ShowSnackBar("Shopping deleted","",1))
                }
            }
            is ShoppingEvent.onToItems->{
                sendEvent(UiEvents.Navigate("items/${event.shopId}"))
            }
            is ShoppingEvent.onBackHome->{
                sendEvent(UiEvents.Navigate("home"))
            }
        }

    }
    private fun sendEvent(event:UiEvents){
        viewModelScope.launch{
            eventSender.send(event)
        }
    }
}