package com.mateusz.quoteapp2.util

import androidx.compose.ui.graphics.vector.ImageVector
import java.io.FileDescriptor

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)
