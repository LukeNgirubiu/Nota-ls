package com.varietapp.NotaLs.Views

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.SVID
import com.varietapp.NotaLs.Screens.onDateChange
import com.varietapp.NotaLs.Utils.ServiceEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.data.Items
import com.varietapp.NotaLs.data.Service
import com.varietapp.NotaLs.data.Shopping
import com.varietapp.NotaLs.repository.ServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(private val repository: ServiceRepository) :ViewModel() {
    val eventSender=Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    var service by mutableStateOf<Service?>(null)
    fun getServices(): Flow<List<Service>> {
        return repository.getAllIServices()
    }
    suspend fun getService(id:Int):Service?{
        return repository.getServiceById(id)
    }
    fun onEvent(event:ServiceEvent){
        when(event){
          is ServiceEvent.onAddService->{
             sendEvents(UiEvents.Navigate(route ="service?id=${event.id}"))
          }
           is ServiceEvent.onHome->{
              sendEvents(UiEvents.Navigate(route = "home"))
            }
            is ServiceEvent.onSave->{
                viewModelScope.launch {
                  repository.insertService(event.service)
                    sendEvents(UiEvents.Navigate(route ="services"))
                }

            }
            is ServiceEvent.onUpdate->{
                service=event.service
                sendEvents(UiEvents.Navigate(route ="service?id=${event.service.id}"))
            }
            is ServiceEvent.onCheck->{
                service=event.service
                viewModelScope.launch{
                    val calender= Calendar.getInstance()
                    val  dueDate="${calender.get(Calendar.YEAR)}-${onDateChange(calender.get(Calendar.MONTH)+1)}-${onDateChange(calender.get(Calendar.DAY_OF_MONTH))}"
                    repository.insertService(event.service.copy(checked = true, Date = dueDate))
                    sendEvents(UiEvents.ShowSnackBar(
                        message = "Checked on a service",
                        action = "Undo",
                    ))
                }
            }
            is ServiceEvent.onUndoAction->{
                viewModelScope.launch{
                    repository.insertService(service!!)
                }
            }
            is ServiceEvent.onDelete->{
                service=event.service
                viewModelScope.launch{
                    repository.deleteService(service!!)
                    sendEvents(UiEvents.ShowSnackBar(
                        message = "Deleted service",
                        action = "Undo",
                    ))
                }
            }
        }

    }
    private fun sendEvents(event:UiEvents){
     viewModelScope.launch {
         eventSender.send(event)
     }
    }
}