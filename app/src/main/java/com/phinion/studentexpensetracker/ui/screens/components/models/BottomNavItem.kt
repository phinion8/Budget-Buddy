package com.phinion.studentexpensetracker.ui.screens.components.models

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val title: String,
    val icon: Painter,
    val route: String
)
