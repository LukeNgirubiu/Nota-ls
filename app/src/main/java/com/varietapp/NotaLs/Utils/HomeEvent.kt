package com.varietapp.NotaLs.Utils

sealed class HomeEvent{
    object onToPreparations:HomeEvent()
    object onToServices:HomeEvent()
    object onToShopping:HomeEvent()
    object onToHelp:HomeEvent()
    object onBackHome:HomeEvent()
}
