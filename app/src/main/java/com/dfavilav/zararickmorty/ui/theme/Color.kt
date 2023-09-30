package com.dfavilav.zararickmorty.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Blue200 = Color(0xFF11B0C8)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)
val StarColor = Color(0xFFFFC94D)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val Colors.statusBarColor
    get() = if (isLight) Purple700 else Color.Black

val Colors.welcomeScreenBackgroundColor
    get() = if (isLight) Color.White else Color.Black

val Colors.titleColor
    get() = if (isLight) DarkGray else LightGray

val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) Blue200 else Color.Black