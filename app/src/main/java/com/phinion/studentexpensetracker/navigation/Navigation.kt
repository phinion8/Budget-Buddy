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
    onClick: () -> Unit
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(onClick = onClick, navController = navController)
        }

        composable(route = Screens.AddUserScreen.route) {
            AddUserScreen()
        }

        composable(route = "add_screen") {
            AddTransactionScreen(navController = navController)
        }

        composable(route = "transaction_list_screen") {
            TransactionListScreen(navController = navController)
        }
        composable(route = Screens.AnalyticsScreen.route)
        {
            AnalyticsScreen(navController = navController)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screens.WelcomeScreen.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = "update_screen/{transactionId}",
            arguments = listOf(
                navArgument("transactionId") {
                    NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val transactionId = navBackStackEntry.arguments?.getString("transactionId")

            Log.d("transactionId", transactionId.toString())

            LaunchedEffect(key1 = true){
                if (transactionId != null) {
                    updateScreenViewModel.getSelectedTransaction(transactionId = transactionId.toInt())
                }
            }



            val selectedTransaction by updateScreenViewModel.selectedTransaction.collectAsState()

            Log.d("selectedTransaction", selectedTransaction.toString())



            selectedTransaction?.let { UpdateScreen(updateScreenViewModel = updateScreenViewModel, navController = navController, transaction = it) }

            }



        }
    }


