package com.mateusz.quoteapp2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    val themeColor = if (!isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(themeColor),
        contentAlignment = Alignment.Center
    ) {

    }
}