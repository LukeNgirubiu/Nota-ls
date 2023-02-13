package com.varietapp.NotaLs

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.varietapp.NotaLs.Screens.*


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navigation(controller: NavHostController,context: Context){
NavHost(navController = controller, startDestination = Routes.home.route ){
    composable(route = Routes.home.route){
        Home(context ,onNavigate = {
            controller.navigate(it.route){
                popUpTo(it.route){
                    inclusive=true
                }
            }
        })
    }
    composable(route = Routes.preps.route){
        preparations(context , onNavigate = {
           controller.navigate(it.route)
        })
    }
    composable(route = Routes.services.route){
        Services(onNavigate = {
            controller.navigate(it.route)
        })
    }
    composable(route = Routes.shop.route){
        Shopping(context , onNavigate = {
            controller.navigate(it.route)
        })
    }
    composable(route = Routes.prepAdd.route,
        arguments = listOf(
            navArgument(PREP_ID){
                type= NavType.IntType
                defaultValue=0
            }
        )
    ){
        val id=it.arguments?.getInt(PREP_ID)
        addPreperation(id!!.toInt(),onNavigate = {
            controller.navigate(it.route){
                popUpTo(it.route){
                    inclusive=true
                }
            }
        }, context = context)
    }
    composable(route = Routes.serviceAdd.route,
        arguments = listOf(
            navArgument(SVID){
                type= NavType.IntType
                defaultValue=0
            }
        )
    ){
        val id=it.arguments?.getInt(SVID)
        AddService(id!!.toInt(),onNavigate = {
         controller.navigate(it.route)
        })
    }


    composable(route = Routes.prepActivities.route,
        arguments = listOf(
            navArgument(PREP_ID){
                type= NavType.IntType
                defaultValue=0
            }
        )
    ){
        val id=it.arguments?.getInt(PREP_ID)
        val date=it.arguments?.getString(Date)
        Active(id!!.toInt(),date!!,onNavigate = {
            controller.navigate(it.route){
                popUpTo(it.route){
                    inclusive=true
                }
            }
        }, context = context)
    }
    composable(route = Routes.Items.route, arguments = listOf(
        navArgument(SHOPID){
            type= NavType.IntType
            defaultValue=0
        }
    )){
        val shoppingId=it.arguments?.getInt(SHOPID)
        Items(shoppId = shoppingId!!, onNavigate = {
            controller.navigate(it.route){
                popUpTo(it.route){
                    inclusive=true
                }
            }
        })
    }
}
}