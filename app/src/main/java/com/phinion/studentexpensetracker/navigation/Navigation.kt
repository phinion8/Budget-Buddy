package com.phinion.studentexpensetracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phinion.studentexpensetracker.ui.screens.home.HomeScreen
import com.phinion.studentexpensetracker.ui.screens.onboarding.WelcomeScreen
import com.phinion.studentexpensetracker.ui.screens.splash.SplashScreen
import com.phinion.studentexpensetracker.ui.screens.transactions.add.AddTransactionScreen
import com.phinion.studentexpensetracker.ui.screens.transactions.list.TransactionListScreen
import com.phinion.studentexpensetracker.ui.screens.transactions.update.UpdateScreen
import com.phinion.studentexpensetracker.ui.screens.transactions.update.viewmodel.UpdateScreenViewModel
import com.phinion.studentexpensetracker.ui.screens.analytics.AnalyticsScreen
import com.phinion.studentexpensetracker.ui.screens.profile.ProfileScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavigation(
    updateScreenViewModel: UpdateScreenViewModel,
    navController: NavHostController,
    onClick: () -> Unit,
    showBottomSheet: (Boolean) -> Unit,
    showFAB: (Boolean) -> Unit,
    onDismissBottomSheet: () -> Unit
) {

    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            showBottomSheet(true)
            showFAB(true)
            HomeScreen(onClick = onClick, navController = navController)
        }

        composable(route = "add_screen") {
            showBottomSheet(false)
            showFAB(false)
            AddTransactionScreen(navController = navController, onDismissBottomSheet = onDismissBottomSheet)
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
        composable(route = Screens.UpdateScreen.route,
            arguments = listOf(
                navArgument(Screens.UpdateScreen.TRANSACTION_ID_ARG) {
                    NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            showBottomSheet(false)
            showFAB(false)
            val transactionId = navBackStackEntry.arguments?.getString(Screens.UpdateScreen.TRANSACTION_ID_ARG)
            if (transactionId != null) {
                UpdateScreen(
                    viewModel = updateScreenViewModel,
                    navController = navController,
                    transactionId = transactionId
                )
            }
        }

    }


}


