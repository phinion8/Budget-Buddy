package com.phinion.studentexpensetracker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.phinion.studentexpensetracker.ui.screens.components.bottombar.BottomNavBar
import com.phinion.studentexpensetracker.ui.screens.components.models.BottomNavItem
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.navigation.SetUpNavigation
import com.phinion.studentexpensetracker.ui.screens.transactions.add.AddTransactionScreen
import com.phinion.studentexpensetracker.ui.screens.transactions.update.viewmodel.UpdateScreenViewModel
import com.phinion.studentexpensetracker.ui.theme.Orange500
import com.phinion.studentexpensetracker.ui.theme.StudentExpenseTrackerTheme
import com.phinion.studentexpensetracker.ui.theme.bottomBackgroundColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentExpenseTrackerTheme {

                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                val updateScreenViewModel: UpdateScreenViewModel by viewModels()

                val bottomSheetState =
                    rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        skipHalfExpanded = true
                    )

                var showBottomBar by remember {
                    mutableStateOf(false)
                }

                var showFloatingActionButton by remember {
                    mutableStateOf(false)
                }

                val scope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        if (showFloatingActionButton) {
                            FloatingActionButton(
                                onClick = {

                                    if (!bottomSheetState.isVisible) {
                                        scope.launch {
                                            bottomSheetState.show()
                                        }
                                    } else {
                                        scope.launch {
                                            bottomSheetState.hide()
                                        }
                                    }


                                },
                                shape = CircleShape,
                                contentColor = Color.White,
                                backgroundColor = Orange500
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "add transaction icon"
                                )
                            }
                        }

                    },
                    content = {

                        ModalBottomSheetLayout(
                            sheetShape = RoundedCornerShape(
                                topStart = 24.dp,
                                topEnd = 24.dp
                            ),
                            sheetState = bottomSheetState,
                            sheetElevation = 16.dp,
                            scrimColor = Color.Black.copy(alpha = 0.5f),
                            content = {
                                BackHandler(enabled = bottomSheetState.isVisible) {
                                    scope.launch {
                                        bottomSheetState.hide()
                                    }
                                }
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                ) {


                                    SetUpNavigation(
                                        navController = navController,
                                        onClick = {
                                            navController.navigate(Screens.Profile.route)
                                        },
                                        updateScreenViewModel = updateScreenViewModel,
                                        showBottomSheet = {
                                            showBottomBar = it
                                        },
                                        showFAB = {
                                            showFloatingActionButton = it
                                        },
                                        onDismissBottomSheet = {

                                            coroutineScope.launch(Dispatchers.Main) {
                                                bottomSheetState.hide()
                                            }
                                        }
                                    )

                                }
                            },
                            sheetContent = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.7f)
                                        .background(com.phinion.studentexpensetracker.ui.theme.backgroundColor)
                                ) {
                                    AddTransactionScreen(
                                        navController = navController,
                                        onDismissBottomSheet = {
                                            coroutineScope.launch(
                                                Dispatchers.Main
                                            ) {
                                                if (bottomSheetState.isVisible) {
                                                    bottomSheetState.hide()
                                                }
                                            }
                                        })
                                }
                            },


                            )

                    },
                    bottomBar = {
                        if (showBottomBar) {
                            BottomAppBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(bottomBackgroundColor),
                                cutoutShape = CircleShape,
                                elevation = 22.dp,
                                backgroundColor = bottomBackgroundColor
                            ) {
                                BottomNavBar(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(bottomBackgroundColor),
                                    bottomNavItems = listOf(
                                        BottomNavItem(
                                            title = "Discover",
                                            icon = painterResource(id = R.drawable.ic_compass),
                                            route = Screens.HomeScreen.route
                                        ),
                                        BottomNavItem(
                                            title = "Analytics",
                                            icon = painterResource(id = R.drawable.ic_analytics),
                                            route = Screens.AnalyticsScreen.route
                                        )
                                    ),
                                    navController = navController,
                                    onItemClick = {
                                        navController.navigate(it.route) {

                                            navController.graph.startDestinationRoute?.let { screen_route ->
                                                popUpTo(screen_route) {
                                                    saveState = true
                                                }
                                            }

                                            this.launchSingleTop = true
                                            this.restoreState = true
                                        }
                                    }
                                )


                            }
                        }

                    },
                )


            }
        }
    }
}



