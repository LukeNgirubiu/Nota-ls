package com.varietapp.NotaLs.Views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.PREP_ID
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.data.Event
import com.varietapp.NotaLs.repository.EventRepository
import com.varietapp.NotaLs.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrepsView @Inject constructor(
    private val repository: EventRepository
)   :ViewModel(){
    val allpreps=repository.getEvents()
    val uiEvent=Channel<UiEvents>()
    val _uiEvent=uiEvent.receiveAsFlow()
    var prep:Event?=null
    suspend fun getPrep(id:Int):Event{
       return repository.getEventById(id)!!
    }
    fun onEvent(event: PrepEvent){
        when(event){
            is PrepEvent.onAddPrep->{
                sendUiEvent(UiEvents.Navigate("addpreps?id=0"))
            }
            is PrepEvent.onEditPrep->{
                sendUiEvent(UiEvents.Navigate("addpreps?id=${event.id}"))
            }
            is PrepEvent.onDeletePrep->{
                prep=event.item
                viewModelScope.launch {
                    repository.deleteEvent(event.item)
                    sendUiEvent(UiEvents.ShowSnackBar(message="Deleted successful",action="Undo"))
                }
            }
            is PrepEvent.undoDelete->{
                viewModelScope.launch{
                    repository.insertEvent(prep!!)
                }
            }
            is PrepEvent.onInsertPrep->{
                viewModelScope.launch {
                    repository.insertEvent(event.prep)
                    sendUiEvent(UiEvents.Navigate("prep"))
                }
            }
            is PrepEvent.backToPreps->{
                sendUiEvent(UiEvents.Navigate("prep"))
            }
            is PrepEvent.backToHome->{
                sendUiEvent(UiEvents.Navigate("home"))
            }
            is PrepEvent.onActivities->{
                sendUiEvent(UiEvents.Navigate("activities/${event.id}/${event.date}"))
            }
        }
    }
    private fun sendUiEvent(event: UiEvents){
        viewModelScope.launch {
            uiEvent.send(event)
        }
    }

}