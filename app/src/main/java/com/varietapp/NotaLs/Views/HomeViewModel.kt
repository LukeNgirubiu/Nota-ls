package com.varietapp.NotaLs.Views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.Utils.HomeEvent
import com.varietapp.NotaLs.Utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {
    val eventSender=Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.onToPreparations->{
                sendEvent(UiEvents.Navigate("prep"))
            }
            HomeEvent.onToShopping->{
                sendEvent(UiEvents.Navigate("shopping"))
            }
            HomeEvent.onToServices->{
                sendEvent(UiEvents.Navigate("services"))
            }

        }
    }
    private fun sendEvent(event:UiEvents){
        viewModelScope.launch {
            eventSender.send(event)
        }
    }
}