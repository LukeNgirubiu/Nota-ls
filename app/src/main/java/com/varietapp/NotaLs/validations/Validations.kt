package com.varietapp.NotaLs

import android.view.View
import com.varietapp.NotaLs.validations.PrepValid
import com.varietapp.NotaLs.validations.ValidData
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
fun prepsValidations(name:String,description:String,date:String):PrepValid{
    var valids = HashMap<String,Boolean>()
    var valid=true
    val errors= arrayOf("","","")
    valids.put("name",true)
    valids.put("description",true)
    valids.put("date",true)
    if (name.isBlank()){
        valids.put("name",false)
        valid=false
        errors[0]="* Name of this preparation is required"
    }
    if (description.isBlank()){
        valids.put("description",false)
        valid=false
        errors[1]="* Description is required"
    }
    if (date.isBlank() || date.equals("Choose Date")){
        valids.put("date",false)
        valid=false
        errors[2]="Date is required"
    }
    if (!date.isBlank() && !date.equals("Choose Date")){
        val calender= Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateBr=date.split("/")
        val stDateStr="${dateBr!![0].trim().toInt()}/${dateBr!![1].trim().toInt()}/${dateBr!![2].trim()}"
        val todayStr="${calender.get(Calendar.DAY_OF_MONTH)}/${calender.get(Calendar.MONTH)+1}/${calender.get(
            Calendar.YEAR)}"
        val todayDate=sdf.parse(todayStr)
        val choiceDate=sdf.parse(stDateStr)
        if(todayDate.after(choiceDate)){
            valids.put("date",false)
            valid=false
            errors[2]="Date required must be today or later"
        }
    }
return  PrepValid(valid,errors,valids)
}

fun validateActivity(time:String,name: String):PrepValid{
    val valids=HashMap<String,Boolean>()
    valids.put("time",true)
    valids.put("name",true)
    val errors= arrayOf("","")
    var valid=true
    if (name.isBlank()){
        errors[0]="* Activity name is required"
        valid=false
        valids.put("name",false)
    }
    if (time.isBlank()||time.equals("Time")){
        errors[1]="* Time is required"
        valid=false
        valids.put("time",false)
    }
    if(time.isNotBlank()&&!time.equals("Time")){
        val calender= Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeNow=time.split(":")
        val nowStr="${calender.get(Calendar.YEAR)}-${calender.get(Calendar.MONTH)+1}-${calender.get(
            Calendar.DAY_OF_MONTH)} ${calender.get(Calendar.HOUR_OF_DAY)}:${calender.get(Calendar.MINUTE)}" +
                ":${calender.get(Calendar.SECOND)}"
        val provideTime="${calender.get(Calendar.YEAR)}-${calender.get(Calendar.MONTH)+1}-${calender.get(
            Calendar.DAY_OF_MONTH)} ${timeNow[0]}:${timeNow[1]}" +
                ":${calender.get(Calendar.SECOND)}"
       val formattedNow=sdf.parse(nowStr)
       val formattedTime=sdf.parse(provideTime)
        if(formattedTime.before(formattedNow)){
            errors[1]="* Time provided must be now or later"
            valid=false
            valids.put("time",false)
        }
    }
    return  PrepValid(valid,errors,valids)
}


fun validateShopping(title:String,description: String,dateDue:String):PrepValid{
    val valids=HashMap<String,Boolean>()
    valids.put("time",true)
    valids.put("name",true)
    valids.put("description",true)
    val errors= arrayOf("","","")
    var valid=true
    if (title.isBlank()){
        errors[0]="* Title name is required"
        valid=false
        valids.put("name",false)
    }
    if (description.isBlank()){
        errors[1]="* Description name is required"
        valid=false
        valids.put("description",false)
    }
    if (dateDue.isBlank()||dateDue.equals("Deadline")){
        errors[2]="* Deadline is required"
        valid=false
        valids.put("time",false)
    }
    if(dateDue.isNotBlank()&&!dateDue.equals("Deadline")){
        val calender= Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val todayStr="${calender.get(Calendar.YEAR)}-${calender.get(Calendar.MONTH)+1}-${calender.get(
            Calendar.DAY_OF_MONTH)}"
        val formattedNow=sdf.parse(todayStr)
        val formattedDate=sdf.parse(dateDue)
        if(formattedDate.before(formattedNow)){
            errors[2]="* Due date provided must be today or later"
            valid=false
            valids.put("time",false)
        }
    }
    return  PrepValid(valid,errors,valids)
}
fun validateShopItem(itemName:String,amount:String,quantity:String):PrepValid{
    val valids=HashMap<String,Boolean>()
    valids.put("item",true)
    valids.put("amount",true)
    valids.put("quantity",true)
    val errors= arrayOf("","","")
    var valid=true
    if (itemName.isBlank()){
        errors[0]="* An item is required"
        valid=false
        valids.put("item",false)
    }
    if (amount.isBlank()){
        errors[1]="* Amount is required"
        valid=false
        valids.put("amount",false)
    }
    if (quantity.isBlank()){
        errors[2]="* Quantity is required"
        valid=false
        valids.put("quantity",false)
    }
    return PrepValid(valid,errors,valids)
}
fun validateService(service:String,amount:String):ValidData{
    val valids= arrayOf(false,false)
    val errors= arrayOf("","")
    var valid=true
    if (service.isBlank()){
        valid=false
        valids[0]=true
        errors[0]="* Service name is required"
    }
    if (amount.isBlank()){
        valid=false
        valids[1]=true
        errors[1]="* Amount is required"
    }
    return ValidData(valid,errors,valids)
}