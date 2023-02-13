package com.varietapp.NotaLs.Views
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varietapp.NotaLs.Utils.ActivityEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.data.EventActivity
import com.varietapp.NotaLs.repository.EventActRepository
import com.varietapp.NotaLs.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val reposository:EventActRepository
) :ViewModel(){
    val eventSender=Channel<UiEvents>()
    val eventReciever=eventSender.receiveAsFlow()
    var isDialogOn by mutableStateOf(false)
    private set
    var activityId:Int? by mutableStateOf(null)
    private set
    var eventActivity by mutableStateOf<EventActivity?>(null)
        private set
    fun setDialogOn(){
        isDialogOn=true
    }
    fun setDialogOff(){
        isDialogOn=false
    }
    fun setActivityId(id:Int){
        activityId=id!!
    }
    fun unSetActivityId(){
        activityId=null
    }
    fun getAllActivities(id: Int):Flow<List<EventActivity>>{
        return reposository.getAllEventsActivity(id)
    }
    suspend fun getActivity():EventActivity{
        return reposository.getEventActivityById(activityId!!)!!
    }
    fun onEvent(event:ActivityEvent){
      when(event){
        is ActivityEvent.onBackPrepare->{
         sendEvent(UiEvents.Navigate("prep"))
        }
          is ActivityEvent.onDelete->{
              viewModelScope.launch {
                  eventActivity=reposository.getEventActivityById(event.id)
                  reposository.deleteEventActivity(eventActivity!!)
                  sendEvent(UiEvents.ShowSnackBar("Delete an activity","",2))
              }
          }
          is ActivityEvent.onEdit->{
             setActivityId(event.id)
              isDialogOn=true
          }
          is ActivityEvent.onInsert->{
              viewModelScope.launch {
                  reposository.insertEventActivity(event.activity!!)
              }
              eventActivity=event.activity
              isDialogOn=false
              unSetActivityId()
              val operationType=if (event.activity.id!=null)"Updated" else "Added"

              sendEvent(UiEvents.ShowSnackBar("${operationType} an activity","",event.checking))
          }
          is ActivityEvent.undoStatus->{
             viewModelScope.launch {
                 viewModelScope.launch {
                     reposository.insertEventActivity(eventActivity!!.copy(done = false)!!)
                 }
             }
          }
          is ActivityEvent.undoDelete->{
              viewModelScope.launch {
                  reposository.insertEventActivity(eventActivity!!)
              }
          }
      }
    }
    private fun sendEvent(event:UiEvents){
        viewModelScope.launch{
            eventSender.send(event)
        }
    }
}