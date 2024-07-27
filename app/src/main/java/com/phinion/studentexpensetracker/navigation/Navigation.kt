package com.phinion.studentexpensetracker.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phinion.studentexpensetracker.navigation.screens.AddUserScreen
import com.phinion.studentexpensetracker.navigation.screens.HomeScreen
import com.phinion.studentexpensetracker.navigation.screens.on_boarding_screen.WelcomeScreen
import com.phinion.studentexpensetracker.navigation.screens.splash_screen.SplashScreen
import com.phinion.studentexpensetracker.navigation.screens.transaction_screen.AddTransactionScreen
import com.phinion.studentexpensetracker.navigation.screens.transaction_screen.TransactionListScreen
import com.phinion.studentexpensetracker.navigation.screens.update_screen.UpdateScreen
import com.phinion.studentexpensetracker.navigation.screens.update_screen.UpdateScreenViewModel
import com.phinion.studentexpensetracker.utils.ScreenState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavigation(
    updateScreenViewModel: UpdateScreenViewModel,
    navController: NavHostController,
    onClick: () -> Unit,
    showBottomSheet: (Boolean) -> Unit,
    showFAB: (Boolean) -> Unit
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            showBottomSheet(true)
            showFAB(true)
            HomeScreen(onClick = onClick, navController = navController)
        }

        composable(route = Screens.AddUserScreen.route) {
            showBottomSheet(false)
            showFAB(false)
            AddUserScreen()
        }

        composable(route = "add_screen") {
            showBottomSheet(false)
            showFAB(false)
            AddTransactionScreen(navController = navController)
        }

        composable(route = "transaction_list_screen") {
            showBottomSheet(false)
            showFAB(false)
            TransactionListScreen(navController = navController)
        }
        composable(route = Screens.AnalyticsScreen.route)
        {
            showBottomSheet(true)
            showFAB(true)
            AnalyticsScreen(navController = navController)
        }
        composable(route = Screens.Profile.route) {
            showBottomSheet(false)
            showFAB(false)
            ProfileScreen(navController = navController)
        }
        composable(route = Screens.WelcomeScreen.route) {
            showBottomSheet(false)
            showFAB(false)
            WelcomeScreen(navController = navController)
        }
        composable(route = Screens.SplashScreen.route) {
            showBottomSheet(false)
            showFAB(false)
            SplashScreen(navController = navController)
        }
        composable(route = "update_screen/{transactionId}",
            arguments = listOf(
                navArgument("transactionId") {
                    NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            showBottomSheet(false)
            showFAB(false)
            val transactionId = navBackStackEntry.arguments?.getString("transactionId")

            LaunchedEffect(key1 = true) {
                if (transactionId != null) {
                    updateScreenViewModel.getSelectedTransaction(transactionId = transactionId.toInt())
                }
            }

            val selectedTransaction by updateScreenViewModel.selectedTransaction.collectAsState()

            selectedTransaction?.let {
                UpdateScreen(
                    updateScreenViewModel = updateScreenViewModel,
                    navController = navController,
                    transaction = it
                )
            }

        }


    }
}


