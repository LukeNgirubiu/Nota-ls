package com.varietapp.NotaLs.Screens
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.PrepsView
import com.varietapp.NotaLs.components.PrepareItem
import com.varietapp.NotaLs.components.deletionActiveDialog

@Composable
fun preparations(context:Context, viewModel:PrepsView= hiltViewModel(), onNavigate: (UiEvents.Navigate)->Unit){
    val allPreps=viewModel.allpreps.collectAsState(initial = emptyList())
    val scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true ){
        viewModel._uiEvent.collect{event->
            when(event){
                is UiEvents.Navigate->onNavigate(event)
                is UiEvents.ShowSnackBar->{
                    val result=scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
//                  if (result==SnackbarResult.ActionPerformed){
                     //   viewModel.onEvent(PrepEvent.undoDelete)
                 //   }
                }
                else->Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(color = colorResource(id = R.color.appColor2)
                    ),
                horizontalArrangement = Arrangement.Start,
                content = {
                    IconButton(onClick = {
                        viewModel.onEvent(PrepEvent.backToHome)
                       },
                        modifier = Modifier.padding(start=20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.back_icon) ,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Text(text = "Preparations",
                        color = Color.White,
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                        modifier = Modifier.padding(top = 7.dp, bottom = 15.dp, start = 30.dp)
                    )
                }
            )
        },
        content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.card_grey))
                .padding(it.calculateBottomPadding())){
                if (allPreps.value.size>0){
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)
                    ){
                        items(allPreps.value){prep->
                            PrepareItem(prep = prep, event = viewModel::onEvent)
                        }
                    }
                }
                else{
                  Row(modifier = Modifier
                      .fillMaxSize(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                      Text(text = "No preparations yet",
                          color = colorResource(id = R.color.titleColor),
                          fontSize = 25.sp,
                          fontWeight = FontWeight.Bold
                          )
                  }
                }
            }
    },
    floatingActionButton = {
         FloatingActionButton(onClick = {
             viewModel.onEvent(PrepEvent.onAddPrep)
         },
         backgroundColor =colorResource(id = R.color.appColor),
         contentColor = Color.White
             ) {
             Icon(imageVector = Icons.Default.Add, contentDescription ="To add" )
         }
    }
    )
    if (viewModel.isDialogOn){
        deletionActiveDialog(viewModel.prep!!.name!!,viewModel::onEvent,viewModel::closeDialog)
    }
}
//Routes.prepAdd.passId(0)
//navHostController.navigate(route = Routes.prepAdd.passId(0))