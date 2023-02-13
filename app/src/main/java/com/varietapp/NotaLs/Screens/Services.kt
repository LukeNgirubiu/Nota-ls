package com.varietapp.NotaLs.Screens
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.ServiceEvent
import com.varietapp.NotaLs.Utils.ShoppingEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ServiceViewModel
import com.varietapp.NotaLs.components.ItemCard
import com.varietapp.NotaLs.components.ServiceCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@Composable
@ExperimentalMaterialApi
fun Services(viewModel: ServiceViewModel= hiltViewModel(), onNavigate: (UiEvents.Navigate)->Unit){
    val allServices=viewModel.getServices().collectAsState(initial = emptyList())
    val scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.eventReciever.collect{event->
            when(event){
                is UiEvents.Navigate->onNavigate(event)
                is UiEvents.ShowSnackBar->{
                    val snackBar=scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (snackBar==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ServiceEvent.onUndoAction)
                    }
                }
             }
        }
    }
    Scaffold(scaffoldState=scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(color = colorResource(id = R.color.appColor2))
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                content = {
                    IconButton(onClick = {
                        viewModel.onEvent(ServiceEvent.onHome)
                    },
                        modifier = Modifier.padding(start=20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.back_icon)
                            , contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Text(text = "Services",
                        color = colorResource(id = R.color.white),
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                        modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.bg_2))
                .padding(it.calculateBottomPadding()),
            contentAlignment = Alignment.Center
            ){
                if (allServices.value.size>0){
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()){
                        itemsIndexed(allServices.value){index, service ->
                            ServiceCard(service =service , event =viewModel::onEvent )
                            if (index<allServices.value.size-1){
                                Divider(color = Color.Black, thickness = 0.5.dp,
                                    modifier = Modifier.padding(bottom = 5.dp,top=10.dp))
                            }
                            else{
                                Spacer(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 30.dp))
                            }
                        }
                    }
                }
                else{
                  Text(text = "No services",
                      style= TextStyle(fontSize = 25.sp,
                          color= colorResource(id = R.color.appColor3),
                          fontFamily = FontFamily(Font(R.font.source_sans_pro_black_italic))
                      )
                  )
              }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
               viewModel.onEvent(ServiceEvent.onAddService(0))
            },
                backgroundColor = colorResource(id = R.color.appColor2),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription ="To add" )
            }
        }
    )
}
