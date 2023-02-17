package com.varietapp.NotaLs.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varietapp.NotaLs.MainActivity
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Routes
import com.varietapp.NotaLs.Utils.HomeEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.HomeViewModel
import com.varietapp.NotaLs.components.eventComponent
import kotlinx.coroutines.flow.collect

@Composable
fun Home(viewModel: HomeViewModel= hiltViewModel(), onNavigate: (UiEvents.Navigate)->Unit) {
    val scroll= rememberScrollState()
    var shoppingToday by remember {
        mutableStateOf<Int?>(0)
    }
    var shoppingFuture by remember {
        mutableStateOf<Int?>(0)
    }
    var eventToday by remember {
        mutableStateOf<Int?>(0)
    }
    var eventFuture by remember {
        mutableStateOf<Int?>(0)
    }
    var countToday by remember {
        mutableStateOf<Int?>(0)
    }
    var countPast by remember {
        mutableStateOf<Int?>(0)
    }
    LaunchedEffect(key1 = true){
        shoppingToday=viewModel.shoppingsToday()
        shoppingFuture=viewModel.shoppingsFuture()
        eventToday=viewModel.countEvents()
        eventFuture=viewModel.futureEvents()
        countToday=viewModel.countToday()
        countPast=viewModel.countPast()
        viewModel.eventReciever.collect{ event->
             when(event){
                 is UiEvents.Navigate->onNavigate(event)
                 else->Unit
             }
        }
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .verticalScroll(scroll)
        .fillMaxWidth()
        .background(color = colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.SpaceEvenly
        ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(100.dp)
            .padding(top = 12.dp)) {
         Column(modifier = Modifier.fillMaxSize()) {
             Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()
             ){
                 Text(text = "Nota ls", modifier = Modifier.padding(
                     start =20.dp,top=10.dp),
                     color = colorResource(id = R.color.titleColor),
                     fontSize = 35.sp,
                     fontWeight = FontWeight.ExtraBold
                 )
             IconButton(onClick = {
              viewModel.onEvent(HomeEvent.onToHelp)
             },
                 modifier = Modifier.padding(end=10.dp)
                 ) {
                 Icon(painter = painterResource(id = R.drawable.help) , contentDescription = "Help")
             }
             }
             Text(text = "Making you note and remember",
                modifier = Modifier.padding(
                 start =20.dp, top = 5.dp, bottom = 30.dp),
                 color = colorResource(id = R.color.titleColor),
                 fontSize = 20.sp,
             )
         }
        }

      eventComponent(modifier = Modifier
          .fillMaxWidth(),
          title="Preparations",
          subTitle = arrayOf("Today","Future"),
          values = arrayOf(eventToday!!,eventFuture!!),
          desc = "Scheduled",
          Navigate = {
          viewModel.onEvent(HomeEvent.onToPreparations)
          }
      )
        Spacer(modifier = Modifier.height(15.dp))
        eventComponent(modifier = Modifier
            .fillMaxWidth(),
            bgColor = colorResource(id = R.color.appColor3),
            title="Shopping's",
            subTitle = arrayOf("Today","Future"),
            values = arrayOf(shoppingToday!!,shoppingFuture!!),
            desc = "Expiry",
            Navigate = {
                viewModel.onEvent(HomeEvent.onToShopping)
        })
        Spacer(modifier = Modifier.height(15.dp))
        eventComponent(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
            title="Services",
            bgColor = colorResource(id = R.color.appColor2)
            , subTitle = arrayOf("Today","Past"),
            values = arrayOf(countToday!!,countPast!!),
            desc = "Checked",
            Navigate= {
                viewModel.onEvent(HomeEvent.onToServices)
            })
    }
}
/*Remember the minimum requirement for this App is android 8 or api 26*/