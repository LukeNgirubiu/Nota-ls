package com.varietapp.NotaLs.Screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun Home(context: Context, viewModel: HomeViewModel= hiltViewModel(), onNavigate: (UiEvents.Navigate)->Unit) {
    LaunchedEffect(key1 = true){
        viewModel.eventReciever.collect{ event->
             when(event){
                 is UiEvents.Navigate->onNavigate(event)
                 else->{

                 }

             }
        }
    }
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(color = colorResource(id = R.color.background))
        ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
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
                 Toast.makeText(context,"Hello luke, here i am",Toast.LENGTH_LONG).show()},
                 modifier = Modifier.padding(end=10.dp)
                 ) {
                 Icon(painter = painterResource(id = R.drawable.help) , contentDescription = "Help")
             }
             }
             Text(text = "Making you note and remember",
                modifier = Modifier.padding(
                 start =30.dp, top = 15.dp),
                 color = colorResource(id = R.color.titleColor),
                 fontSize = 20.sp,
             )
         }
        }
      eventComponent(modifier = Modifier
          .fillMaxHeight(0.3f)
          .fillMaxWidth(),
          title="Preparations", subTitle = arrayOf("Today","Future"),
          values = arrayOf(20,8),
          Navigate = {
          viewModel.onEvent(HomeEvent.onToPreparations)
          }
      )
        Spacer(modifier = Modifier.height(15.dp))
        eventComponent(modifier = Modifier
            .fillMaxHeight(0.43f)
            .fillMaxWidth(),
            bgColor = colorResource(id = R.color.appColor3),
            title="Shopping's", subTitle = arrayOf("Today","Future"),
            values = arrayOf(25,15),
            Navigate = {
                viewModel.onEvent(HomeEvent.onToShopping)
        })
        Spacer(modifier = Modifier.height(15.dp))
        eventComponent(modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth(),
            title="Services",
            bgColor = colorResource(id = R.color.appColor2)
            , subTitle = arrayOf("Today","Past"),
            values = arrayOf(25,15),
            Navigate= {
                viewModel.onEvent(HomeEvent.onToServices)
            })
    }
}
/*Remember the minimum requirement for this App is android 8 or api 26*/