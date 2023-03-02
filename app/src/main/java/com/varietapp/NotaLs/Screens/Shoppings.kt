package com.varietapp.NotaLs.Screens

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.ItemsEvent
import com.varietapp.NotaLs.Utils.ShoppingEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ShoppingViewModel
import com.varietapp.NotaLs.components.deletionShoppingDialog
import com.varietapp.NotaLs.components.shoppingDialog
import com.varietapp.NotaLs.components.shoppingItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Shopping(context: Context,onNavigate: (UiEvents.Navigate)->Unit, viewModel:ShoppingViewModel= hiltViewModel()){
    val allShoppings=viewModel.getAllShoppings().collectAsState(initial = emptyList())
    var scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true){
     viewModel.eventReciever.collect{
         when(it){
             is UiEvents.Navigate->onNavigate(it)
             is UiEvents.ShowSnackBar->{
                scaffoldState.snackbarHostState.showSnackbar(it.message,it.action)
             }
         }
     }
    }
Scaffold(
    scaffoldState = scaffoldState,
    topBar = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .background(color = colorResource(id = R.color.appColor3))
            .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Start,
            content = {
                IconButton(onClick = {
                   viewModel.onEvent(ShoppingEvent.onBackHome)
                },
                    modifier = Modifier.padding(start=20.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.back_icon)
                        , contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(text = "Shoppings",
                    color = colorResource(id = R.color.white),
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                    modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                )
            }
        )
    },
    content = {
        if (allShoppings.value.size>0){
         Box(modifier = Modifier
             .fillMaxSize()
             .background(color = colorResource(id = R.color.card_grey))
             .padding(it.calculateBottomPadding())){
             LazyColumn(modifier = Modifier
                 .fillMaxSize()
                 .padding(top = 5.dp)
             ){
                 items(allShoppings.value){
                     shoppingItem(it,viewModel::onEvent)
                 }
             }
         }
        }
        else{
          Column(
              modifier = Modifier
                  .fillMaxSize()
                  .background(color = colorResource(id = R.color.card_grey))
                  .padding(it.calculateBottomPadding()),
                  horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
           ){
              Text(text = "No shoppings is available",
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
            viewModel.onEvent(ShoppingEvent.onAdd)
        },
            backgroundColor = colorResource(id = R.color.appColor3),
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription ="To add" )
        }
    }
)
    if (viewModel.isDeleteDialogOn){
        deletionShoppingDialog(viewModel.shopping!!.title,viewModel::onEvent,viewModel::closeDelete)
    }
}