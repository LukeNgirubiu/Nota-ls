package com.varietapp.NotaLs.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import  com.varietapp.NotaLs.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.Utils.ServiceEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ServiceViewModel
import com.varietapp.NotaLs.data.Service
import com.varietapp.NotaLs.validateService
import java.util.*

@Composable
fun AddService(serviceId:Int, onNavigate:(UiEvents.Navigate)->Unit,viewModel: ServiceViewModel= hiltViewModel()){
    var service by remember {
        mutableStateOf("")
    }
    var expense by remember {
        mutableStateOf("")
    }
    var errors by remember {
        mutableStateOf(arrayOf("",""))
    }
    var errorsFields by remember {
        mutableStateOf(arrayOf(false,false))
    }
    var statusTxt by remember {
        mutableStateOf("Add")
    }
    LaunchedEffect(key1 = true){
        println("Service id $serviceId")
        if(serviceId>0){
            val serv=viewModel.getService(serviceId)
            service=serv!!.name
            expense=serv!!.cost.toString()
            statusTxt="Update"
        }
        viewModel.eventReciever.collect{
            when(it){
               is UiEvents.Navigate->onNavigate(it)
                else->Unit
            }
        }
    }
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.appColor2))
                .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                content = {
                    Text(text = "$statusTxt Service",
                        color = colorResource(id = R.color.white),
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                        modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.bg_2))
                    .padding(it.calculateBottomPadding()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Service",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 5.dp, top = 10.dp),
                    style=TextStyle(fontSize = 18.sp,
                        color= Color.Black,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                TextField(value =service,
                    onValueChange ={if(it.length<31) service=it},
                    singleLine = false,
                    isError =errorsFields[0],
                    maxLines = 3,
                    textStyle= TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor2)),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor =  colorResource(id =R.color.appColor2),
                        focusedIndicatorColor = colorResource(id = R.color.appColor2)
                    )
                )
                Text(text = "${errors[0]}",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(start = 5.dp, bottom = 5.dp),
                    style=TextStyle(fontSize = 16.sp,
                        color=Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                Text(text = "Amount Spent",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(bottom = 5.dp),
                    style=TextStyle(fontSize = 18.sp,
                        color=Color.Black,
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                TextField(value =expense,
                    onValueChange ={if(it.length<7) expense=it},
                    singleLine = true,
                    isError =errorsFields[1],
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle= TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor2)),
                    modifier = Modifier.fillMaxWidth(0.9f),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor =  colorResource(id = R.color.appColor2),
                        focusedIndicatorColor = colorResource(id = R.color.appColor2)
                    )
                )
                Text(text = "${errors[1]}",
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(start = 5.dp),
                    style=TextStyle(fontSize = 16.sp,
                        color=Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                Row(modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 15.dp)
                ) {
                    Button(onClick = {
                    val validate= validateService(service, expense)
                        errors=validate.errors
                        errorsFields=validate.feildsValid
                        if (validate.valid){
                            val serveId=if(serviceId==0) null else serviceId
                          viewModel.onEvent(ServiceEvent.onSave(Service(name = service,
                              cost = expense.toDouble(),
                              id = serveId
                          )))
                        }
                        else{
                           if(!validate.feildsValid[0]){
                               errors[0]=""
                               errorsFields[0]=false
                           }
                            if(!validate.feildsValid[1]){
                                errors[1]=""
                                errorsFields[1]=false
                            }
                        }
                    },
                        elevation = ButtonDefaults.elevation(2.dp,2.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .clip(RoundedCornerShape(5.dp))
                        ,
                        contentPadding = PaddingValues(10.dp,10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.appColor2))
                    ) {
                        Text(text = "$statusTxt",
                            textAlign = TextAlign.Center,
                            color= Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    )
}