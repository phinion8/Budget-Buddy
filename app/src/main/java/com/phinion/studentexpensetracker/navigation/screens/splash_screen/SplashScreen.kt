package com.phinion.studentexpensetracker.navigation.screens.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.navigation.screens.on_boarding_screen.WelcomeViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navController: NavController
) {


    val readOnBoardingState by splashViewModel.readOnBoardingState.collectAsState()

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .weight(9f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_budget),
                    contentDescription = "budget icon",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Student Expense Manager")
            }

        }

        Text(
            text = "Made with ❤️ in India by PS", textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().weight(1f).padding(all = 16.dp)
        )


    }

}