@file:OptIn(ExperimentalComposeUiApi::class)

package com.varietapp.NotaLs.components
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.Screens.onDateChange
import com.varietapp.NotaLs.Utils.*
import com.varietapp.NotaLs.Views.ActivitiesViewModel
import com.varietapp.NotaLs.Views.ItemsViewModel
import com.varietapp.NotaLs.Views.ShoppingViewModel
import com.varietapp.NotaLs.data.*
import com.varietapp.NotaLs.validateActivity
import com.varietapp.NotaLs.validateShopItem
import com.varietapp.NotaLs.validateShopping
import java.time.LocalDate
import java.util.*

@Composable
fun eventComponent(modifier: Modifier,bgColor: Color=colorResource(id = R.color.appColor),sbtColor:Color=colorResource(id = R.color.white),
                   title:String,subTitle:Array<String>,values:Array<Int>,Navigate:()->Unit){//,onClick:()->Unit
    Card(modifier = modifier
        .fillMaxHeight()
        .fillMaxWidth(0.9f)
        .padding(all = 7.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        contentColor = Color.White
        ) {
      Column(modifier= Modifier
          .fillMaxSize()
          .background(bgColor)) {
          Row(modifier= Modifier
              .fillMaxWidth()
              .padding(start = 20.dp, end = 20.dp, top = 15.dp),
              horizontalArrangement = Arrangement.SpaceBetween) {
           Text(text = "$title", fontWeight = FontWeight.Bold,
           fontSize = 25.sp,
               )
              IconButton(onClick = {
                  Navigate()
                  },
                  modifier = Modifier.padding(end=10.dp)
              ) {
                  Icon(painter = painterResource(id = R.drawable.navigate_in) , contentDescription = "Nav_in")
              }
          }
          Row(modifier = Modifier
              .fillMaxWidth()
              .padding(5.dp),
              horizontalArrangement = Arrangement.SpaceEvenly
          ) {
              HomeItems(modifier = Modifier.fillMaxWidth(0.48f),"${subTitle[0]}",values[0],sbtColor)
              HomeItems(modifier = Modifier.fillMaxWidth(0.48f),"${subTitle[1]}",values[1],sbtColor)
          }

      }
    }
}
@Composable
fun HomeItems(modifier: Modifier,heading:String,number:Int,sbtColor:Color){
    Column(modifier = modifier) {
        Text(text = heading, fontWeight = FontWeight.Medium,
            fontSize = 25.sp,
            modifier = Modifier.padding(top = 3.dp)
        )
        Text(text = "$number",
            fontWeight = FontWeight.Light,
            fontSize = 30.sp,
            color= sbtColor,
            modifier = Modifier.padding(top = 1.dp)
        )
    }
}
@Composable
fun PrepareItem(prep:Event,event:(PrepEvent)->Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 40.dp)
        .padding(start = 5.dp, end = 10.dp, bottom = 5.dp)
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    event(PrepEvent.onEditPrep(prep.id!!))
                }
            )
        },
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
         ) {
            val formatDate=prep.Date.split("-").reversed().joinToString("/")
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Start) {
                Text(text =prep.name!!, style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.source_sans_pro_bold)
                    ),
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(start = 15.dp, top = 5.dp))
                Text(text =formatDate, style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp),
                    color = colorResource(id = R.color.appColor))
            }
            Text(text =prep.description,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.source_sans_pro_regular)
                    ),
                    fontSize = 19.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(start = 15.dp,bottom = 10.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)) {
                Button(onClick = {
               event(PrepEvent.onActivities(prep.id!!,prep.Date))
                },
                    elevation = ButtonDefaults.elevation(2.dp,2.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentPadding = PaddingValues(10.dp,10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.appColor2))
                ) {
                    Text(text = "Activities",
                        textAlign = TextAlign.Center,
                        color= Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Button(onClick = {
                 event(PrepEvent.onDeletePrep(prep))
                }, elevation = ButtonDefaults.elevation(2.dp,2.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    contentPadding = PaddingValues(10.dp,10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.red_1))
                ) {
                    Text(text = "Delete",
                        textAlign = TextAlign.Center,
                        color= Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun activitiesDialog(prepId:Int, dismis:()->Unit, viewHolder:ActivitiesViewModel= hiltViewModel(), context: Context, activityId:Int?){
    Dialog(onDismissRequest ={} , properties = DialogProperties(
        usePlatformDefaultWidth = false
    )) {
        var activity by remember {
            mutableStateOf("")
        }
        var time by remember {
            mutableStateOf("Time")
        }
        var activeValid by remember {
            mutableStateOf(false)
        }
        var timeValid by remember {
            mutableStateOf(false)
        }
        var errors by remember {
            mutableStateOf(arrayOf("",""))
        }
        var done by remember {
            mutableStateOf(false)
        }
        var submitLabel by remember {
            mutableStateOf("Add")
        }
        LaunchedEffect(key1 = true){
            if (activityId!=null){
                var activeEvent=viewHolder.getActivity()
                done=activeEvent.done
                activity=activeEvent.name
                time=activeEvent.time
                submitLabel="Update"
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth(0.9f)
            .heightIn(50.dp)
        ,
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
          Column(modifier = Modifier
              .fillMaxWidth()
              .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
              Row(modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 10.dp),
              horizontalArrangement = Arrangement.SpaceBetween){
                  val activityTitle=if(activityId==0) "Add" else "Update"
                  Text(text = "$activityTitle Activity",
                      modifier = Modifier.padding(top = 15.dp, start = 20.dp),
                      style=TextStyle(fontSize = 22.sp,
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                  IconButton(onClick = {
                      dismis()
                     },
                      modifier = Modifier.padding(end=10.dp)
                  ) {
                      Icon(painter = painterResource(id = R.drawable.close_dialog) , contentDescription = "close")
                  }
              }
              Row(modifier = Modifier.fillMaxWidth(),){
                  Text(text = "Name",
                      modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                      style=TextStyle(fontSize = 18.sp,
                      color=colorResource(id = R.color.appColor3),
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
              }
              TextField(value =activity ,
                  onValueChange ={if(it.length<26) activity=it},
                  singleLine = false,
                  maxLines = 3,
                  isError = activeValid,
                  textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
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
                      style=TextStyle(fontSize = 18.sp,
                          color=Color.Red,
                          fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
              }
              Row(modifier = Modifier.fillMaxWidth(),){
                  Text(text = "Time",
                      modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                      style=TextStyle(fontSize = 18.sp,
                          color=colorResource(id = R.color.appColor3),
                          fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
              }
              Button(onClick = {
                  val calender= Calendar.getInstance()
                  val timePickerDialog = TimePickerDialog(
                      context,
                      {_, hour : Int, minute: Int ->
                          val min=if (minute<10)"0$minute" else "$minute"
                          val hr=if (hour<10)"0$hour" else "$hour"
                          time= "$hr:$min"
                      }, calender.get(Calendar.HOUR_OF_DAY), calender.get(Calendar.MINUTE), true
                  )
                  timePickerDialog.show()
              },
                  elevation = ButtonDefaults.elevation(2.dp,2.dp),
                  modifier = Modifier
                      .fillMaxWidth()
                      .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                      .clip(RoundedCornerShape(5.dp)),
                  contentPadding = PaddingValues(10.dp,10.dp),
                  colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                   border = BorderStroke(1.dp,if(timeValid) Color.Red else colorResource(id = R.color.appColor3))
              ) {
                  Text(text = "$time",
                      textAlign = TextAlign.Center,
                      color= if(timeValid) Color.Red else colorResource(id = R.color.appColor3),
                      fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                      fontSize = 22.sp )
              }
              Row(modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = 5.dp),){
                  Text(text = "${errors[1]}",
                      modifier = Modifier.padding(start = 20.dp),
                      style=TextStyle(fontSize = 18.sp,
                          color=Color.Red,
                          fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
              }
              Button(onClick = {
                 val vl= validateActivity( name = activity,time=time)
                  if (vl.valid){
                      errors= arrayOf("","")
                      activeValid=false
                      timeValid=false
                      viewHolder.onEvent(ActivityEvent.onInsert(
                          EventActivity(activityId,activity,time,prepId,done)
                      ))
                  }
                  else{
                      if(!vl.feildsValid.get("name")!!){
                          activeValid=true
                          errors[0]=vl.errors[0]
                      }
                      if(vl.feildsValid.get("name")!!){
                          activeValid=false
                          errors[0]=vl.errors[0]
                      }
                      if(!vl.feildsValid.get("time")!!){
                          timeValid=true
                          errors[1]=vl.errors[1]
                      }
                      if(vl.feildsValid.get("time")!!){
                          timeValid=false
                          errors[1]=vl.errors[1]
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
               Text(text = "$submitLabel",
                   textAlign = TextAlign.Center,
                   color= Color.White,
                   fontFamily = FontFamily(Font(R.font.source_sans_pro_bold)),
                   fontSize = 22.sp )
              }
          }
        }
    }

}

@Composable
fun activityCard(eventActivity: EventActivity,event:(ActivityEvent)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        event(ActivityEvent.onEdit(eventActivity.id!!))
                    }
                )
            },
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
  Column(modifier = Modifier
      .fillMaxWidth()
      .heightIn(20.dp)
      .background(color = colorResource(id = R.color.card_grey))) {
      Text(text =eventActivity.name!!, style = TextStyle(
          fontFamily = FontFamily(
              Font(R.font.source_sans_pro_bold)
          ),
          fontSize = 22.sp,
          color = colorResource(id = R.color.appColor2)
      ), modifier = Modifier.padding(start = 15.dp,top=10.dp, bottom = 10.dp))
      Text(text =eventActivity.time, style = MaterialTheme.typography.subtitle1,
          modifier = Modifier.padding(start = 15.dp,bottom = 10.dp),
          color = Color.Red)
      Row(modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween
          ){

          Checkbox(checked = eventActivity.done, onCheckedChange ={ isChecked->
              val updater=eventActivity.copy(done = isChecked)
              event(ActivityEvent.onInsert(updater,1))
          },
              enabled = if(eventActivity.done) false else true,
              colors = CheckboxDefaults.colors(Color.Red)
          )
          IconButton(onClick = {
              event(ActivityEvent.onDelete(eventActivity.id!!))
          },
              modifier = Modifier.padding(start=20.dp)
          ) {
              Icon(painter = painterResource(id = R.drawable.delete_items) , contentDescription = "Back", tint = colorResource(
                  id = R.color.red_1
              ))
          }
      }
  }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun shoppingDialog(context:Context,viewModel: ShoppingViewModel= hiltViewModel()){
    Dialog(onDismissRequest ={} , properties = DialogProperties(
        usePlatformDefaultWidth = false
    )) {
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
        var dateDue by remember {
            mutableStateOf("Deadline")
        }
       var btnTxt by remember {
           mutableStateOf("")
       }
        var dateValid by remember {
            mutableStateOf(false)
        }
        var errors by remember {
            mutableStateOf(arrayOf("","",""))
        }

        LaunchedEffect(key1 = true){
           btnTxt=if(viewModel.dialogueType==2) "Update" else "Add"
            if (viewModel.dialogueType==2){
                title=viewModel.shopping!!.title
                description=viewModel.shopping!!.description
                dateDue=viewModel.shopping!!.dateDue
            }

        }
        Card(modifier = Modifier
            .fillMaxWidth(0.9f)
            .heightIn(50.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){

                    Text(text = "Shopping",
                        modifier = Modifier.padding(top = 15.dp, start = 20.dp),
                        style=TextStyle(fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                    IconButton(onClick = {
                     viewModel.onEvent(ShoppingEvent.onCloseDialog)
                    },
                        modifier = Modifier.padding(end=10.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.close_dialog) , contentDescription = "close")
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),){
                    Text(text = "Title",
                        modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                        style=TextStyle(fontSize = 18.sp,
                            color=colorResource(id = R.color.appColor3),
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                }
                TextField(value =title ,
                    onValueChange ={ if (it.length <= 35) title = it
                                   },
                    singleLine = true,
                    isError = titleInValid,
                    textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
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
                        style=TextStyle(fontSize = 18.sp,
                            color=Color.Red,
                            fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                }
                Row(modifier = Modifier.fillMaxWidth(),){
                    Text(text = "Description",
                        modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                        style=TextStyle(fontSize = 18.sp,
                            color=colorResource(id = R.color.appColor3),
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                }
                TextField(value =description ,
                    onValueChange ={ if (it.length <= 50) description = it },
                    singleLine = false,
                    maxLines = 3,
                    isError=descriptionInValid,
                    textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
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
                        style=TextStyle(fontSize = 18.sp,
                            color=Color.Red,
                            fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                }


                Row(modifier = Modifier.fillMaxWidth(),){
                    Text(text = "Time",
                        modifier = Modifier.padding(bottom = 5.dp, start = 20.dp),
                        style=TextStyle(fontSize = 18.sp,
                            color=colorResource(id = R.color.appColor3),
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
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
                        style=TextStyle(fontSize = 18.sp,
                            color=Color.Red,
                            fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                }
                Button(onClick = {
                   val valid=validateShopping(title,description,dateDue)
                    errors=valid.errors
                    if (valid.valid){
                        errors=arrayOf("","","")
                        titleInValid=false
                        descriptionInValid=false
                        dateValid=false
                        viewModel.onEvent(ShoppingEvent.onSave(Shopping(title = title,
                        description = description,
                        dateDue = dateDue,
                     )))

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
    }

}

@Composable
fun shoppingItem(shopping: Shopping,event:(ShoppingEvent)->Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .heightIn(40.dp)
        .padding(bottom = 5.dp, start = 10.dp, end = 10.dp),
    elevation = 5.dp,
    shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top

        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
            horizontalArrangement = Arrangement.End
        ){
            IconButton(onClick = {
                event(ShoppingEvent.onToItems(shopping.id!!))
            },
                modifier = Modifier.padding(end=10.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = "close",
                    tint = colorResource(id = R.color.appColor3)
                )
            }
        }
            Text(text =shopping.title, style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_bold)
                ),
                fontSize = 22.sp,
                color = colorResource(id = R.color.appColor3)

            ), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 5.dp)
                )

            Text(buildAnnotatedString {
               withStyle(style = SpanStyle(
                   color = colorResource(id = R.color.appColor3)
                   )){
                   append("Expiry  ")
               }
                append(shopping.dateDue.split("-").reversed().joinToString("/"))
            },
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.source_sans_pro_regular)
                    ),
                    fontSize = 18.sp,
                    color = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 7.dp)
            )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, bottom = 15.dp, top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(onClick = {
                event(ShoppingEvent.onOpenDialogUpdate(shopping))
            }, elevation = ButtonDefaults.elevation(2.dp,2.dp),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentPadding = PaddingValues(10.dp,10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.appColor3))
            ) {
                Text(text = "Update",
                    textAlign = TextAlign.Center,
                    color= Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }

            Button(onClick = {
              event(ShoppingEvent.onDelete(shopping))
            }, elevation = ButtonDefaults.elevation(2.dp,2.dp),
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentPadding = PaddingValues(10.dp,10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(text = "Delete",
                    textAlign = TextAlign.Center,
                    color= Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ItemDialog(viewModel:ItemsViewModel){
    Dialog(onDismissRequest ={} , properties = DialogProperties(
        usePlatformDefaultWidth = false
    )) {
        var item by remember {
            mutableStateOf("")
        }
        var itemValid by remember {
            mutableStateOf(false)
        }
        var totalAmount by remember {
        mutableStateOf("")
        }
        var totalAmountValid by remember {
            mutableStateOf(false)
        }

        var quantity by remember {
            mutableStateOf("")
        }
        var quantityValid by remember {
            mutableStateOf(false)
        }
        var errors by remember {
            mutableStateOf(arrayOf("","",""))
        }
        var title by remember {
            mutableStateOf("")
        }
        var btnTxt by remember {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = true) {
           title=if(viewModel.dialogueType==2) "Update Item" else "Add an Item"
           btnTxt=if(viewModel.dialogueType==2) "Update" else "Add"
           if(viewModel.dialogueType==2){
             item=viewModel.currItem!!.name
             totalAmount=viewModel.currItem!!.cost.toString()
             quantity=viewModel.currItem!!.quantity.toString()
           }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(50.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "$title",
                        modifier = Modifier.padding(top = 15.dp, start = 20.dp),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))
                        )
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(ItemsEvent.closeDialog)
                        },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close_dialog),
                            contentDescription = "close"
                        )
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, start = 10.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Item",
                        style=TextStyle(fontSize = 18.sp,
                            color=colorResource(id = R.color.appColor3),
                            fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                }
                TextField(value =item,
                    onValueChange ={ if (it.length <= 50) item = it },
                    singleLine = false,
                    maxLines = 3,
                    isError=itemValid,
                    textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .padding(horizontal = 3.dp),
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
                        style=TextStyle(fontSize = 18.sp,
                            color=Color.Red,
                            fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp, start = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(end = 7.dp)) {
                        Text(text = "Total Cost",
                            modifier = Modifier.padding(bottom = 5.dp),
                            style=TextStyle(fontSize = 18.sp,
                                color=colorResource(id = R.color.appColor3),
                                fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                        TextField(value =totalAmount,
                            onValueChange ={ if (it.length <= 10) totalAmount = it },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError=totalAmountValid,
                            textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor =  colorResource(id = R.color.appColor3),
                                focusedIndicatorColor = colorResource(id = R.color.appColor3)
                            )
                        )
                        Text(text = "${errors[1]}",
                            modifier = Modifier.padding(start = 5.dp),
                            style=TextStyle(fontSize = 18.sp,
                                color=Color.Red,
                                fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                    }
                    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                        Text(text = "Quantity",
                            modifier = Modifier.padding(bottom = 5.dp, start = 4.dp),
                            style=TextStyle(fontSize = 18.sp,
                                color=colorResource(id = R.color.appColor3),
                                fontFamily = FontFamily(Font(R.font.source_sans_pro_bold))))
                        TextField(value =quantity,
                            onValueChange ={ if (it.length <= 5) quantity = it },
                            singleLine = true,
                            isError=quantityValid,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle=TextStyle(fontSize = 22.sp, color = colorResource(id = R.color.appColor3)),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor =  colorResource(id = R.color.appColor3),
                                focusedIndicatorColor = colorResource(id = R.color.appColor3)
                            )
                        )
                        Text(text = "${errors[2]}",
                            modifier = Modifier.padding(start = 5.dp),
                            style=TextStyle(fontSize = 18.sp,
                                color=Color.Red,
                                fontFamily = FontFamily(Font(R.font.ropa_sans_italic))))
                    }
                }
                Button(onClick = {
                  val valid=validateShopItem(item,totalAmount,quantity)
                    errors=valid.errors
                  if(valid.valid){
                     errors= arrayOf("","","")
                      itemValid=false
                      totalAmountValid=false
                      quantityValid=false
                      totalAmount.toDouble()
                      viewModel.onEvent(ItemsEvent.onSave(item,totalAmount.toDouble(),quantity.toInt()))
                  }
                  else{
                      if (!valid.feildsValid.get("item")!!){
                          itemValid=true
                      }
                      if (valid.feildsValid.get("item")!!){
                          itemValid=false
                      }
                      if (!valid.feildsValid.get("amount")!!){
                          totalAmountValid=true
                      }
                      if (valid.feildsValid.get("amount")!!){
                          totalAmountValid=false
                      }
                      if (!valid.feildsValid.get("quantity")!!){
                          quantityValid=true
                      }
                      if (valid.feildsValid.get("quantity")!!){
                          quantityValid=false
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
    }
}
@Composable
fun ItemCard(item:Items,event:(ItemsEvent)->Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(30.dp)
            .padding(horizontal = 5.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        println("Onlong press")
                        event(ItemsEvent.openDialog(item.shoppId, 2, item))
                    }
                )
            }

    ) {
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Checkbox(checked = item.ticked, onCheckedChange ={
                 event(ItemsEvent.onTickItem(item))
            },
                modifier = Modifier.fillMaxWidth(0.1f),
                enabled = if(item.ticked) false else true,
                colors = CheckboxDefaults.colors(Color.Red)
            )
            Text(text =item.name, style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.ropa_sans_regular)
                ),
                fontSize = 22.sp,
                color = Color.Black

            ), modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(start = 15.dp, top = 10.dp)
            )
            Text(text =item.quantity.toString(), style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_bold)
                ),
                fontSize = 20.sp,
                color = colorResource(id = R.color.appColor3)
            ), modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(onClick = {
                event(ItemsEvent.onDelete(item))
            },
                modifier = Modifier.fillMaxWidth(0.1f)
            ) {
                Icon(painter = painterResource(id = R.drawable.delete_items) , contentDescription = "delete items")
            }
        Text(text ="Ksh "+item.cost.toString(), style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_regular)
                ),
                fontSize = 20.sp,
                color = colorResource(id = R.color.red_1)
            ), modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = 10.dp, start = 30.dp)
            )
    }

    }
}


@Composable
fun ServiceCard(service:Service,event:(ServiceEvent)->Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(30.dp)
            .padding(horizontal = 5.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                    event(ServiceEvent.onUpdate(service))
                    }
                )
            }

    ) {
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Checkbox(checked = service.checked, onCheckedChange ={
                  event(ServiceEvent.onCheck(service))
            },
                modifier = Modifier.fillMaxWidth(0.1f),
                enabled = if(service.checked) false else true,
                colors = CheckboxDefaults.colors(Color.Red)
            )
            Text(text =service.name, style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_bold)
                ),
                fontSize = 20.sp,
                color = Color.Black

            ), modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(start = 15.dp, top = 10.dp)
            )

            Text(text ="${if(service.Date!=null) service.Date!!.split("-").reversed().joinToString("/") else "Unchecked"}", style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.ropa_sans_italic)
                ),
                fontSize = 18.sp,
                color = colorResource(id = R.color.appColor2)

            ), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 10.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(onClick = {
             event(ServiceEvent.onDelete(service))
            },
                modifier = Modifier.fillMaxWidth(0.1f)
            ) {
                Icon(painter = painterResource(id = R.drawable.delete_items) , contentDescription = "delete service")
            }
            Text(text ="Ksh "+service.cost.toString(), style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.source_sans_pro_regular)
                ),
                fontSize = 20.sp,
                color = colorResource(id = R.color.red_1)
            ), modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(top = 10.dp, start = 30.dp)
            )
        }

    }
}
@Composable
fun doneWithArch(modifier: Modifier,heading:String,percent:Float){
    Column(modifier = modifier) {
        Text(text = heading, fontFamily = FontFamily(
            Font(R.font.ropa_sans_regular, FontWeight.Medium)
        ),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp, start = 30.dp)
        )
        BoxWithConstraints(modifier = modifier.padding(top=15.dp),
            contentAlignment = Alignment.Center
        ) {
            val maxWidth=constraints.maxWidth
            Canvas(modifier = Modifier
                .fillMaxSize(maxWidth * 0.8f)
                .padding(4.dp)
            ){
                drawCircle(
                    brush = Brush.horizontalGradient(colors = listOf(Color.White,Color.White)),
                    radius = size.width/2,
                    style = Stroke(22f)
                )
                val degree=percent/100*360
                drawArc(
                    brush = Brush.linearGradient(colors = listOf(Color(224, 61, 32),Color(224, 61, 32))),
                    startAngle = -90f,
                    sweepAngle = degree,
                    useCenter = false,
                    style = Stroke(24f, cap = StrokeCap.Round)
                )
            }
            Text(text ="${percent.toInt()}%\nDone",color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(R.font.ropa_sans_italic, FontWeight.SemiBold)
                ) )
        }

    }
}
//                val updater=eventActivity.copy(done = isChecked)
//                event(ActivityEvent.onInsert(updater,1))
//colorResource(id = R.color.appColor3)
/*
buildAnnotatedString {
withStyle(style = SpanStyle(
   color = colorResource(id = R.color.appColor3)
   )){
   append("Expiry  ")
}
append(shopping.dateDue.split("-").reversed().joinToString("/"))
}
* */