package com.varietapp.NotaLs.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.HomeEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.HomeViewModel



@Composable
fun Help(viewModel: HomeViewModel = hiltViewModel(), onNavigate:(UiEvents.Navigate)->Unit){
    val scroll= rememberScrollState()
    LaunchedEffect(key1 = true){
        viewModel.eventReciever.collect{event->
            when(event){
                is UiEvents.Navigate->onNavigate(event)
                else->Unit
            }
        }
    }
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .background(
                    color = colorResource(id = R.color.appColor2)
                ),
                horizontalArrangement = Arrangement.Start,
                content = {
                    IconButton(onClick = {
                    viewModel.onEvent(HomeEvent.onBackHome)
                    },
                        modifier = Modifier.padding(start=20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.back_icon) ,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Text(text = "Help",
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
                .verticalScroll(scroll)
                .padding(it.calculateBottomPadding())) {
              Column(modifier = Modifier
                  .heightIn(40.dp)
                  .fillMaxWidth()
                  .padding(horizontal = 5.dp)
              ) {
                  Text(text ="Introduction", style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_bold)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(top = 10.dp, bottom = 2.dp)
                  )
                  Text(text = stringResource(id = R.string.intro), style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_regular)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 10.dp)
                  )
                  Text(text ="Preparations", style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_bold)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 2.dp)
                  )
                  Text(text = stringResource(id = R.string.preparation), style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_regular)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 10.dp)
                  )
                  Text(text ="Shopping", style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_bold)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 2.dp)
                  )
                  Text(text = stringResource(id = R.string.shopping), style = TextStyle(
                      fontFamily = FontFamily(
                          Font(R.font.source_sans_pro_regular)
                      ),
                      fontSize = 20.sp,
                      color = colorResource(id = R.color.black)
                  ), modifier = Modifier
                      .fillMaxWidth()
                      .padding(bottom = 10.dp)
                  )
                }
            }
        }

    )
}

