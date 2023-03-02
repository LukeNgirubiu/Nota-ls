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
    var isDeleteDialogOn by mutableStateOf(false)
        private set
    var shopping by mutableStateOf<Shopping?>(null)
        private set
    val eventSender= Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    fun getAllShoppings(): Flow<List<Shopping>> {
        return repository.getAllShopping()
    }
    suspend fun  getShopping(id:Int):Shopping?{
        return repository.getShoppingById(id)
    }
    fun closeDelete(){
        isDeleteDialogOn=false
        shopping=null
    }
    fun onEvent(event:ShoppingEvent){
        when(event){
            is ShoppingEvent.onAdd->{
                sendEvent(UiEvents.Navigate("add_shopping/-1"))
            }
            is ShoppingEvent.onSave->{
                viewModelScope.launch {
                    repository.insertShopping(event.shopping)
                    if (event.shopping.id!=null){
                        sendEvent(UiEvents.ShowSnackBar("Shopping updated",""))
                    }
                    sendEvent(UiEvents.Navigate("shopping"))
                }
            }
            is ShoppingEvent.onUpdate->{
                sendEvent(UiEvents.Navigate("add_shopping/${event.shopp_id}"))
            }
            is ShoppingEvent.onDeleteDialog->{
                shopping=event.shopping
                isDeleteDialogOn=true
            }
            is ShoppingEvent.onDelete->{
                viewModelScope.launch {
                    repository.deleteShopping(shopping!!)
                    closeDelete()
                }
            }
            is ShoppingEvent.onToItems->{
                sendEvent(UiEvents.Navigate("items/${event.shopId}/${event.currency}"))
            }
            is ShoppingEvent.onBackHome->{
                sendEvent(UiEvents.Navigate("home"))
            }
            else -> {}
        }

    }
    private fun sendEvent(event:UiEvents){
        viewModelScope.launch{
            eventSender.send(event)
        }
    }
}