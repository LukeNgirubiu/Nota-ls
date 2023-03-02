package com.varietapp.NotaLs.Screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.ShoppingEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.ShoppingViewModel
import com.varietapp.NotaLs.validateShopping
import java.util.*
@Composable
fun addShopping(shop_id:Int?,viewModel: ShoppingViewModel = hiltViewModel(),
                onNavigate:(UiEvents.Navigate)->Unit, context: Context){
   var title by remember {
        mutableStateOf("")
    }
    var titleInValid by remember {
        mutableStateOf(false)
    }
    var description by remember {
        mutableStateOf("")
    }
    var descriptionInValid by remember {
        mutableStateOf(false)
    }
    var currency by remember {
        mutableStateOf("Us$.")
    }
    var currencyInValid by remember {
        mutableStateOf(false)
    }
    var dateDue by remember {
        mutableStateOf("Deadline")
    }
    var btnTxt by remember {
        mutableStateOf("Add")
    }
    var dateValid by remember {
        mutableStateOf(false)
    }
    var errors by remember {
        mutableStateOf(arrayOf("","","",""))
    }
    LaunchedEffect(key1 = true){
        print("Shopping ${shop_id}")
        if (shop_id!!>-1){
            btnTxt="Update"
            val shopping=viewModel.getShopping(shop_id!!)
            title=shopping!!.title
            description=shopping!!.description
            dateDue=shopping!!.dateDue
            currency=shopping.currency
        }
        viewModel.eventReciever.collect{
            when(it){
                is UiEvents.Navigate->onNavigate(it)
                else -> Unit
            }
        }
    }

    Scaffold(
      topBar = {
          Row(modifier = Modifier
              .fillMaxWidth()
              .background(color = colorResource(id = R.color.appColor3))
              .padding(bottom = 5.dp),
              horizontalArrangement = Arrangement.Start,
              content = {
                  Text(text = "Shopping Form",
                      color = colorResource(id = R.color.white),
                      fontSize = 25.sp,
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                      modifier = Modifier.padding(top = 7.dp, bottom = 10.dp, start = 30.dp)
                  )
              }
          )
        },
    content={
        Column(modifier = Modifier
            .padding(it.calculateBottomPadding())
            .fillMaxWidth()
            .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(),){
                Text(text = "Title",
                    modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= colorResource(id = R.color.appColor3),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                    )
                )
            }
            TextField(value =title ,
                onValueChange ={ if (it.length <= 35) title = it
                },
                singleLine = true,
                isError = titleInValid,
                textStyle= TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor =  colorResource(id = R.color.appColor3),
                    focusedIndicatorColor = colorResource(id = R.color.appColor3)
                )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),){
                Text(text = "${errors[0]}",
                    modifier = Modifier.padding(start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))
                    )
                )
            }
            Row(modifier = Modifier.fillMaxWidth(),){
                Text(text = "Description",
                    modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= colorResource(id = R.color.appColor3),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                    )
                )
            }
            TextField(value =description ,
                onValueChange ={ if (it.length <= 50) description = it },
                singleLine = false,
                maxLines = 3,
                isError=descriptionInValid,
                textStyle= TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor =  colorResource(id = R.color.appColor3),
                    focusedIndicatorColor = colorResource(id = R.color.appColor3)
                )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),){
                Text(text = "${errors[1]}",
                    modifier = Modifier.padding(start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))
                    )
                )
            }


            Row(modifier = Modifier.fillMaxWidth(),){
                Text(text = "Currency",
                    modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= colorResource(id = R.color.appColor3),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                    )
                )
            }
            TextField(value =currency,
                onValueChange ={ if (it.length <= 7) currency = it },
                singleLine = true,
                isError=currencyInValid,
                textStyle= TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor =  colorResource(id = R.color.appColor3),
                    focusedIndicatorColor = colorResource(id = R.color.appColor3)
                )
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),){
                Text(text = "${errors[3]}",
                    modifier = Modifier.padding(start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))
                    )
                )
            }

            Row(modifier = Modifier.fillMaxWidth(),){
                Text(text = "Time",
                    modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= colorResource(id = R.color.appColor3),
                        fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                    )
                )
            }
            Button(onClick = {
                val calender= Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        dateDue="$year-${onDateChange(month + 1)}-${onDateChange(dayOfMonth)}"
                    },
                    calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH),
                )

                datePickerDialog.show()
            },
                elevation = ButtonDefaults.elevation(2.dp,2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentPadding = PaddingValues(10.dp,10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                border = BorderStroke(1.dp,if(dateValid) Color.Red else colorResource(id = R.color.appColor3))
            ) {
                Text(text = "$dateDue",
                    textAlign = TextAlign.Center,
                    // color= if(timeValid) Color.Red else colorResource(id = R.color.appColor3),
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                    fontSize = 22.sp )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),){
                Text(text = "${errors[2]}",
                    modifier = Modifier.padding(start = 20.dp),
                    style= TextStyle(fontSize = 18.sp,
                        color= Color.Red,
                        fontFamily = FontFamily(Font(R.font.ropa_sans_italic))
                    )
                )
            }
            Button(onClick = {
                val valid= validateShopping(title,description,dateDue,currency)
                errors=valid.errors
                if (valid.valid){
                    errors=arrayOf("","","","")
                    titleInValid=false
                    descriptionInValid=false
                    dateValid=false
                    viewModel.onEvent(ShoppingEvent.onSave(
                       com.varietapp.NotaLs.data.Shopping(
                           id=if(shop_id!!>-1) shop_id else null,
                           title = title,
                           description = description,
                           dateDue = dateDue,
                           currency=currency
                       )
                    )
                    )
                }
                else{
                    if (!valid.feildsValid.get("name")!!){
                        titleInValid=true
                    }
                    if (valid.feildsValid.get("name")!!){
                        titleInValid=false
                    }
                    if (!valid.feildsValid.get("description")!!){
                        descriptionInValid=true
                    }
                    if (valid.feildsValid.get("description")!!){
                        descriptionInValid=false
                    }
                    if (!valid.feildsValid.get("time")!!){
                        dateValid=true
                    }
                    if (valid.feildsValid.get("time")!!){
                        dateValid=false
                    }
                    if (!valid.feildsValid.get("currency")!!){
                        dateValid=true
                    }
                    if (valid.feildsValid.get("currency")!!){
                        dateValid=false
                    }
                }
            },
                elevation = ButtonDefaults.elevation(2.dp,2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentPadding = PaddingValues(10.dp,10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.appColor3))
            ) {
                Text(text = "$btnTxt",
                    textAlign = TextAlign.Center,
                    color= Color.White,
                    fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                    fontSize = 22.sp )
            }
        }
        }
    )
}








