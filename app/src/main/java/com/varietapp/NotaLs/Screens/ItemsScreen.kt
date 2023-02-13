package com.varietapp.NotaLs.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.ItemsEvent
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.ShoppingEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ItemsViewModel
import com.varietapp.NotaLs.components.ItemCard
import com.varietapp.NotaLs.components.ItemDialog
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun Items(shoppId:Int, viewModel: ItemsViewModel= hiltViewModel(),onNavigate:(UiEvents.Navigate)->Unit){
    val allItems=viewModel.getAllItems(shoppId).collectAsState(initial = emptyList())
    val scaffoldState= rememberScaffoldState()
    var sumCost by remember {
        mutableStateOf(0.0)
    }
    var allItemsCount by remember {
        mutableStateOf(0)
    }
    var checkedOn by remember {
        mutableStateOf(0.0)
    }
    var checkedOnCount by remember {
        mutableStateOf(0)
    }
    var shoppingName by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var expiryDate by remember {
        mutableStateOf("")
    }

    if(allItems.value.size>0){
        val allCost=allItems.value.map { it.cost }
        sumCost=allCost.sum()
        allItemsCount=allCost.size
        val checked=allItems.value.filter { it.ticked }.map { it.cost }
        checkedOn=checked.sum()
        checkedOnCount=checked.size
    }
    else{
        sumCost=0.0
        allItemsCount=0
        checkedOn=0.0
        checkedOnCount=0
    }
    LaunchedEffect(key1 = true){
        val shop=viewModel.getShop(shoppId)
        shoppingName=shop!!.title
        description=shop!!.description
        expiryDate=shop!!.dateDue
        viewModel.eventReciever.collect{ event->
            when(event){
                is UiEvents.Navigate->onNavigate(event)
                is UiEvents.ShowSnackBar->{
                    val snackBar=scaffoldState.snackbarHostState.showSnackbar(event.message,event.action)
                  when(event.status){
                      1->{
                       if (snackBar==SnackbarResult.ActionPerformed){
                              viewModel.onEvent(ItemsEvent.onUndoAction)
                         }
                      }
                  }
                }
            }
        }
    }
Scaffold(
    scaffoldState=scaffoldState,
    topBar = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .background(color = colorResource(id = R.color.appColor3)),
            horizontalArrangement = Arrangement.Start,
            content = {
                IconButton(onClick = {
                 viewModel.onEvent(ItemsEvent.onToShop)
                },
                    modifier = Modifier.padding(start=20.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.back_icon)
                        , contentDescription = "Back",
                    tint = Color.White
                    )
                }
                Text(text = "Summary",
                    color = colorResource(id = R.color.white),
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                    modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                )
            }
        )
    },
    content = {
      Column(modifier = Modifier
          .fillMaxSize()
          .background(color = colorResource(id = R.color.appColor3))
          .padding(it.calculateBottomPadding())) {
          Box(modifier = Modifier
              .fillMaxWidth()
              .heightIn(40.dp)
              .padding(bottom = 10.dp)){
              Column(modifier = Modifier
                  .fillMaxWidth()
                  .padding(start = 30.dp)
               ) {
                  Text(text = "$shoppingName",
                      color= Color.White,
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                      fontSize = 22.sp,
                      modifier = Modifier.fillMaxWidth()
                  )
                  Text(
                      buildAnnotatedString {
                          withStyle(style = SpanStyle(
                              color =Color.White
                          )){
                              append("Expiry  ")
                          }
                          append(expiryDate.split("-").reversed().joinToString("/"))
                      },
                      color= colorResource(id = R.color.red_1),
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                      fontSize = 20.sp,
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(top = 6.dp, bottom = 2.dp)
                  )
                  Text(text = "$description",
                      color= Color.White,
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                      fontSize = 20.sp,
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(top = 6.dp, bottom = 10.dp)
                  )
                  Row(modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceEvenly
                  ) {
                      Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                          Text(text = "$allItemsCount",
                              color= Color.White,
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                              fontSize = 20.sp,
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(start = 20.dp)
                          )
                          Text(text = "Ksh $sumCost",
                              color= Color.White,
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                              fontSize = 22.sp,
                              modifier = Modifier.fillMaxWidth()
                          )
                          Text(text = "Total",
                              color= Color.White,
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                              fontSize = 24.sp,
                              modifier = Modifier.fillMaxWidth()
                          )
                      }
                      Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                          Text(text = "$checkedOnCount",
                              color= colorResource(id = R.color.red_1),
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                              fontSize = 22.sp,
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(start = 20.dp)
                          )
                          Text(text = "Ksh $checkedOn",
                              color= Color.White,
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_regular)),
                              fontSize = 22.sp,
                              modifier = Modifier.fillMaxWidth()
                          )
                          Text(text = "Checked",
                              color= Color.White,
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                              fontSize = 24.sp,
                              modifier = Modifier.fillMaxWidth()
                          )
                      }
                  }
              }
          }
          Box(
              Modifier
                  .fillMaxSize()
                  .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                  .background(Color.White)
            ){
              if (allItems.value.size==0){
                  Row(modifier = Modifier
                      .fillMaxSize()
                      .padding(it.calculateBottomPadding()),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.Center
                  ) {
                      Text(text = "No items",
                          style= TextStyle(fontSize = 25.sp,
                              color= colorResource(id = R.color.appColor3),
                              fontFamily = FontFamily(Font(R.font.source_sans_pro_black_italic))
                          )
                      )
                  }
              }
              else{
                  LazyColumn(modifier = Modifier
                      .fillMaxSize()
                      .padding(it.calculateBottomPadding())){
                      itemsIndexed(allItems.value){ index, item ->
                          ItemCard(item = item,viewModel::onEvent)
                          if (index<allItems.value.size-1){
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
          }
     }
    },
    floatingActionButton = {
        FloatingActionButton(onClick = {
          viewModel.onEvent(ItemsEvent.openDialog(shoppId))
        },
            backgroundColor = colorResource(id = R.color.appColor3),
            contentColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription ="To add" )
        }
    }
)
    if (viewModel.isDialogOn){
        ItemDialog(viewModel)
    }
}