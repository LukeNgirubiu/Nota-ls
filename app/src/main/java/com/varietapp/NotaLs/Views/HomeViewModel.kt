package com.varietapp.NotaLs.Views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.Screens.onDateChange
import com.varietapp.NotaLs.Utils.HomeEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.repository.EventRepository
import com.varietapp.NotaLs.repository.ServiceRepository
import com.varietapp.NotaLs.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingRepo: ShoppingRepository,
    private val eventRepo:EventRepository,
    private val serviceRepo:ServiceRepository
):ViewModel() {
    val eventSender=Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    val calender= Calendar.getInstance()
    val dateToday="${calender.get(Calendar.YEAR)}-${onDateChange(calender.get(Calendar.MONTH)+1)}-${onDateChange(calender.get(Calendar.DAY_OF_MONTH))}"
    suspend fun shoppingsToday():Int?{
        return shoppingRepo.countShopping(dateToday)
    }
    suspend fun shoppingsFuture():Int?{
        return shoppingRepo.futureShopping(dateToday)
    }
    suspend fun countEvents():Int?{
       return eventRepo.countEvents(dateToday)
    }
    suspend fun futureEvents():Int?{
        return eventRepo.futureEvents(dateToday)
    }
    suspend fun countToday():Int?{
        return serviceRepo.countToday(dateToday)
    }
    suspend fun countPast():Int?{
        return serviceRepo.countPast(dateToday)
    }
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
            HomeEvent.onToHelp->{
                sendEvent(UiEvents.Navigate("help"))
            }
            HomeEvent.onBackHome->{
                sendEvent(UiEvents.Navigate("home"))
            }

        }
    }
    private fun sendEvent(event:UiEvents){
        viewModelScope.launch {
            eventSender.send(event)
        }
    }
}