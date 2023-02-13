package com.varietapp.NotaLs.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import com.varietapp.NotaLs.R
import com.varietapp.NotaLs.data.Event
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun preparationsForm(modifier:Modifier,scope: CoroutineScope,sheetState: BottomSheetScaffoldState,controller: NavHostController,stateType:Int=0,event:Event?=null){
    Column(modifier = modifier,
    ) {
        var preparationName by remember {
            mutableStateOf("")
        }
        var descriptions by remember {
            mutableStateOf("")
        }
        var dueDate by remember {
            mutableStateOf("Due Date")
        }
        //State type 0 for new prep 1 for update
        val submitTxt=if (stateType==0) "Add" else "Update"


      Row(modifier = modifier
          .fillMaxWidth(0.9f)
          .padding(end = 10.dp, top = 15.dp), horizontalArrangement = Arrangement.End) {
          IconButton(onClick = {   scope.launch {
              sheetState.bottomSheetState.collapse()
          }
           }) {
           Icon(painter = painterResource(id =R.drawable.close_dialog ), contentDescription = "Close btn")
          }
      }
       OutlinedTextField(value = preparationName,
           onValueChange = {preparationName=it},
           textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
           modifier = modifier
               .fillMaxWidth(0.9f)
               .padding(start = 10.dp, end = 10.dp),
           label = {
               Text(text = "Preparation Name")
           },
           singleLine = true,
          // keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
       )
        Spacer(modifier =Modifier.height(10.dp))
        OutlinedTextField(value = descriptions,
            onValueChange = {descriptions=it},
            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(horizontal = 10.dp),
            label = {
                Text(text = "Describe this preparation")
            },
            singleLine = true,
//            colors = TextFieldDefaults.textFieldColors()
        shape = RoundedCornerShape(3.dp),

        )
        Spacer(modifier =Modifier.height(10.dp) )
        OutlinedTextField(
            value = dueDate,
            onValueChange = { dueDate = it },

            textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Normal),
            modifier = modifier
                .fillMaxWidth(0.9f)
                .padding(horizontal = 10.dp),
            label = {
                Text(text = "Date dd-mm-yyy")
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(

            ),
        )
        //keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        Spacer(modifier =Modifier.height(10.dp) )
        Button(onClick = { /*TODO*/ },
        shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.elevation(0.dp,0.dp),
            modifier = modifier.fillMaxWidth(0.7f),
            contentPadding = PaddingValues(0.dp,5.dp),
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
