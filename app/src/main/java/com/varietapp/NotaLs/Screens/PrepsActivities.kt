package com.varietapp.NotaLs.Screens
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.ActivityEvent
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ActivitiesViewModel
import com.varietapp.NotaLs.components.activitiesDialog
import com.varietapp.NotaLs.components.activityCard
import com.varietapp.NotaLs.data.EventActivity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Active(id:Int,date:String, viewModel: ActivitiesViewModel= hiltViewModel(), context: Context, onNavigate: (UiEvents.Navigate)->Unit){
    var scaffoldState= rememberScaffoldState()
    val allActivities=viewModel.getAllActivities(id).collectAsState(initial = emptyList())
    LaunchedEffect(key1 = true){
         viewModel.eventReciever.collect{
           when(it){
           is UiEvents.Navigate->onNavigate(it)
           is UiEvents.ShowSnackBar->{
               val undo=if (it.status==1||it.status==2) "Undo" else ""
              val snack=scaffoldState.snackbarHostState.showSnackbar(it.message,undo)
               if (snack==SnackbarResult.ActionPerformed){
                   if (it.status==1){
                    viewModel.onEvent(ActivityEvent.undoStatus)
                   }
                   if (it.status==2){
                       viewModel.onEvent(ActivityEvent.undoDelete)
                   }
               }
           }
               else->Unit
         }
        }
     }
    Scaffold(scaffoldState = scaffoldState
        ,modifier = Modifier.fillMaxWidth(),
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .padding(bottom = 5.dp)
                    //
                .background(color = colorResource(id = R.color.appColor)),
                horizontalArrangement = Arrangement.Start,
                content = {
                    IconButton(onClick = {
                        viewModel.onEvent(ActivityEvent.onBackPrepare)
                    },
                        modifier = Modifier.padding(start=20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.back_icon) ,
                            contentDescription = "Back",
                        tint = Color.White)
                    }
                    Text(text = "Activities",
                        color = colorResource(id = R.color.white),
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                        modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                    )
                }
            )
        },
       content = {
                 Column(modifier = Modifier.fillMaxSize().padding(it.calculateBottomPadding())) {
                     LazyColumn(modifier = Modifier.fillMaxSize().padding(it.calculateBottomPadding())){
                      items(allActivities.value){
                          activityCard(eventActivity= it,event=viewModel::onEvent)
                      }
                  }
                 }
         },
        floatingActionButton = {
            val calender= Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val todayStr="${calender.get(Calendar.YEAR)}-${calender.get(Calendar.MONTH)+1}-${calender.get(
                Calendar.DAY_OF_MONTH)}"
            val parsedNow=sdf.parse(todayStr)
            val parsedDate=sdf.parse(date)
            if (!parsedDate.after(parsedNow)){
                FloatingActionButton(onClick = {
                    viewModel.setDialogOn()
                },
                    backgroundColor = colorResource(id = R.color.appColor),
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription ="To add" )
                }
            }
        }
    )
    if (viewModel.isDialogOn){
        activitiesDialog(prepId = id, dismis = viewModel::setDialogOff,context=context, activityId = viewModel.activityId)
    }


}