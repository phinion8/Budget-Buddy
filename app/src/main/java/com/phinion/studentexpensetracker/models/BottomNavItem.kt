package com.phinion.studentexpensetracker.models

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val title: String,
    val icon: Painter,
    val route: String
)
