package com.varietapp.NotaLs.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun help(){
    val scroll= rememberScrollState()
    Scaffold(
        topBar = {},
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(it.calculateBottomPadding())) {
                
            }
        }

    )
}

