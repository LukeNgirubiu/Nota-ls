package com.varietapp.NotaLs.Utils

sealed class UiEvents {
    data class Navigate(val route: String) : UiEvents()
    data class ShowSnackBar(
        val message: String,
        val action: String, val status: Int? = null
    ) : UiEvents()
}
