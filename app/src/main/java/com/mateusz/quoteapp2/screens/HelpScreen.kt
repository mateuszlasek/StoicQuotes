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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HelpScreen() {

    val themeColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(themeColor),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            Text(
                text = "Stoicism is a school of philosophy that hails from ancient " +
                        "Greece and Rome in the early parts of the 3rd century, BC. " +
                        "It is a philosophy of life that maximizes positive " +
                        "emotions, reduces negative emotions and helps individuals " +
                        "to hone their virtues of character.",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Stoic Quotes app is a hand-picked collection of inspirational" +
                        "quotes on Stoicism from Roman and Greek philosophers",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}