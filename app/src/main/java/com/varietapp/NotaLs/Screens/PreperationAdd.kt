package com.varietapp.NotaLs.Screens
import android.app.DatePickerDialog
import android.content.Context
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Utils.PrepEvent
import com.varietapp.NotaLs.Utils.UiEvents
import com.varietapp.NotaLs.Views.PrepsView
import com.varietapp.NotaLs.data.Event
import com.varietapp.NotaLs.prepsValidations
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun addPreperation(stateType:Int,viewModel: PrepsView = hiltViewModel(), onNavigate: (UiEvents.Navigate)->Unit, context:Context){
    var preparationName by remember {
        mutableStateOf("")
    }
    var prepNameValid by remember {
        mutableStateOf(false)
    }
    var descriptions by remember {
        mutableStateOf("")
    }
    var descriptionsValid by remember {
        mutableStateOf(false)
    }
    var dueDate by remember {
        mutableStateOf("Choose Date")
    }
    var dueDateValid by remember {
        mutableStateOf(false)
    }
    var errors by remember {
        mutableStateOf(arrayOf("","",""))
    }
    val scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true){
        if (stateType>0){
            val prep=viewModel.getPrep(stateType)
            preparationName=prep.name!!
            descriptions=prep.description
            dueDate=prep.Date.split("-").reversed().joinToString("/")
        }

        viewModel._uiEvent.collect{event->
          when(event){
              is UiEvents.Navigate->onNavigate(event)
              else->Unit
          }
        }
    }
   Scaffold(
       scaffoldState=scaffoldState,
       modifier = Modifier.fillMaxSize(),
       topBar = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(color = colorResource(id = R.color.background)),
                    horizontalArrangement = Arrangement.Center,
                content = {
                    Text(text = "Preparation Form",
                       color = colorResource(id = R.color.titleColor),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                        )
                }
                    )
       },
   content = {
       Column(modifier = Modifier
           .fillMaxSize()
           .padding(it.calculateBottomPadding())
           .background(color = colorResource(id = R.color.background)),
           verticalArrangement = Arrangement.Top,
           horizontalAlignment = Alignment.Start
       ) {
           //State type 0 for new prep 1 for update
           val submitTxt=if (stateType==0) "Add" else "Update"


           val feildsColor=TextFieldDefaults.textFieldColors(
               focusedIndicatorColor = colorResource(id = R.color.teal_700),
               focusedLabelColor = colorResource(id = R.color.red_1),
               backgroundColor = Color.White,
               textColor = colorResource(id = R.color.appColor),
               cursorColor = colorResource(id = R.color.appColor)
           )
           OutlinedTextField(value = preparationName,
               onValueChange = {preparationName=it},
               textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
               label = {
                   Text(text = "Preparation Name",
                       fontSize = 18.sp,
                       fontWeight = FontWeight.W500
                   )
               },
               singleLine = true,
               isError = prepNameValid,
               shape = RoundedCornerShape(7.dp),
               colors = feildsColor
           )
           Text(
               text = "${errors[0]}",
               fontSize = 20.sp,
               color=Color.Red,
               fontStyle = FontStyle.Italic,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp),
           )
           Spacer(modifier = Modifier.height(5.dp))
           OutlinedTextField(
               value = descriptions,
               onValueChange = { descriptions = it },
               textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
               label = {
                   Text(text = "Describe this preparation",
                       fontSize = 18.sp,
                       fontWeight = FontWeight.W500
                   )
               },
               singleLine = true,
               colors = feildsColor,
               shape = RoundedCornerShape(7.dp),
               isError = descriptionsValid,
               maxLines = 4

               )
           Text(
               text = "${errors[1]}",
               fontSize = 20.sp,
               color=Color.Red,
               fontStyle = FontStyle.Italic,
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp),
           )
           Spacer(modifier = Modifier.height(5.dp) )
           Button(onClick = {
               val calender= Calendar.getInstance()
               val datePickerDialog = DatePickerDialog(
                   context,
                   { _, year, month, dayOfMonth ->
                      dueDate="${onDateChange(dayOfMonth)}/${onDateChange(month + 1)}/$year"
                   },
                   calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH),
               )

             datePickerDialog.show()
           },
               elevation = ButtonDefaults.elevation(2.dp,2.dp),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp)
                   .clip(RoundedCornerShape(10.dp)),
               contentPadding = PaddingValues(10.dp,10.dp),
               colors = ButtonDefaults.buttonColors( backgroundColor = colorResource(id = R.color.appColor)),
                //border = BorderStroke(2.dp, if(dueDateValid) Color.Red else colorResource(id = R.color.appColor))
           ) {
               Text(text = "$dueDate",
                   textAlign = TextAlign.Center,
                   color= Color.White,
                   fontWeight = FontWeight.Light,
                   fontSize = 22.sp,
                   modifier = Modifier
                       .fillMaxWidth(0.6f)

               )
           }
           //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
           Spacer(modifier = Modifier.height(15.dp) )
           Button(onClick = {
              val valid= prepsValidations(preparationName ,descriptions,dueDate)
               if(valid.valid){
                   val dbDate=dueDate.split("/").reversed().joinToString("-")
                   val id =if (stateType==0) null else stateType
                   viewModel.onEvent(PrepEvent.onInsertPrep(Event(id=id,name = preparationName, description = descriptions,Date=dbDate, activaties = 0)))

               }
               else{
                   prepNameValid=!valid.feildsValid.get("name")!!
                   if (prepNameValid){
                       errors[0]=valid.errors[0]
                   }
                   if (!prepNameValid){
                       errors[0]=""
                   }
                   descriptionsValid=!valid.feildsValid.get("description")!!
                   if (descriptionsValid){
                       errors[1]=valid.errors[1]
                   }
                   if (!descriptionsValid){
                       errors[1]=""
                   }
                   dueDateValid=!valid.feildsValid.get("date")!!
                   if(dueDateValid==true){
                       Toast.makeText(context,valid.errors[2],Toast.LENGTH_SHORT).show()
                   }
               }
           },
               //shape = RoundedCornerShape(20.dp),
               elevation = ButtonDefaults.elevation(2.dp,2.dp),
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp)
                   .clip(RoundedCornerShape(10.dp)),
               contentPadding = PaddingValues(10.dp,10.dp),
               colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.appColor))
               // border = BorderStroke(2.dp, Color.Black)
           ) {
               Text(text = "$submitTxt",
                   textAlign = TextAlign.Center,
                   color= Color.White,
                   fontWeight = FontWeight.Bold,
                   fontSize = 22.sp
               )
           }

       }
   }
       )
}
fun onDateChange(value:Int):String {
    return if (value<10) "0$value" else "$value"
}
// keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)