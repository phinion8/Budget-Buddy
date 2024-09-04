package com.phinion.studentexpensetracker.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.ui.screens.splash.viewmodel.SplashViewModel
import com.phinion.studentexpensetracker.ui.theme.backgroundColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navController: NavController
) {

    val readOnBoardingState by splashViewModel.readOnBoardingState.collectAsState()
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(backgroundColor)
    systemUiController.setNavigationBarColor(backgroundColor)

    LaunchedEffect(key1 = true) {
        delay(1500)
        navController.popBackStack()
        if (readOnBoardingState) {
            navController.navigate(Screens.HomeScreen.route)
        } else {
            navController.navigate(Screens.WelcomeScreen.route)
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().background(backgroundColor),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .weight(9f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
            )

        }


    }

}