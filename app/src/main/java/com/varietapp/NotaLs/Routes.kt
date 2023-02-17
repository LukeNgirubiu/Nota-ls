package com.varietapp.NotaLs
val PREP_ID="PREP_ID"
val Date="date"
val SHOPID="shopId"
val SVID="svId"
sealed class Routes(val route:String) {
    object home:Routes(route = "home")
    object preps:Routes(route = "prep")
    object prepAdd:Routes(route="addpreps?id={$PREP_ID}")
    object shop:Routes(route = "shopping")
    object services:Routes(route = "services")
    object help:Routes(route = "help")
    object serviceAdd:Routes(route="service?id={$SVID}")
    object prepActivities:Routes(route="activities/{$PREP_ID}/{$Date}")
    object Items:Routes(route="items/{$SHOPID}")
}