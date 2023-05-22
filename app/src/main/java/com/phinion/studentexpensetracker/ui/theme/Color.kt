package com.phinion.studentexpensetracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val appPrimaryColor = Color(0xFF42224A)
val appPrimaryVariant = Color(0xFF8F659A)
val Orange500 = Color(0xFFef8767)
val appLightColor = Color(0xFFFAE6FF)
val darkBackgroundColor = Color(0xFF151419)
val DarkGray800 = Color(0xFF43414B)
val darkBottomBarColor = Color(0xFF252429)
val mediumWhite = Color(0xFFEEEEEE)



val backgroundColor: Color
    @Composable
    get() =
        if (isSystemInDarkTheme()) darkBackgroundColor else Color.White

val bottomBackgroundColor: Color
@Composable
get() =
    if (isSystemInDarkTheme()) darkBottomBarColor else Color.White

val outcomeSectionColor: Color
@Composable
get() =
        if (isSystemInDarkTheme()) DarkGray800 else appPrimaryColor

val backgroundTextColor: Color
@Composable
get() =
        if (isSystemInDarkTheme()) Color.White else appPrimaryColor

val buttonBackgroundColor: Color
@Composable
get() =
    if (isSystemInDarkTheme()) Orange500 else appPrimaryColor

val onBoardingTextColor: Color
@Composable
get() =
    if(isSystemInDarkTheme()) darkBottomBarColor else appPrimaryColor

val bottomBarContentColor: Color
@Composable
get() =
    if (isSystemInDarkTheme()) Color.White else appPrimaryColor