package com.phinion.studentexpensetracker.ui.screens.transactions.list

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.ui.screens.home.viewmodel.HomeViewModel
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.ui.screens.home.NoItemScreen
import com.phinion.studentexpensetracker.ui.screens.home.RedBackground
import com.phinion.studentexpensetracker.ui.screens.home.TransactionItem
import com.phinion.studentexpensetracker.ui.theme.appPrimaryColor
import com.phinion.studentexpensetracker.ui.theme.backgroundColor
import com.phinion.studentexpensetracker.ui.theme.backgroundTextColor
import com.phinion.studentexpensetracker.utils.RequestState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionListScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {


    val transactionList by homeViewModel.allTransaction.collectAsState()

    val currency by homeViewModel.currencyValue.collectAsState()


    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(appPrimaryColor)
    systemUiController.setNavigationBarColor(backgroundColor)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        AppTopBar(navController = navController, title = "Transactions")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Swipe left to delete any transaction.", textAlign = TextAlign.Center,
            color = backgroundTextColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            if (transactionList is RequestState.Success) {


                if ((transactionList as RequestState.Success<List<Transaction>>).data.isNotEmpty()) {
                    items(
                        items = (transactionList as RequestState.Success<List<Transaction>>).data,
                        key = {
                            it.id
                        }) { transition ->


                        val dismissState = rememberDismissState()
                        val dismissDirection = dismissState.dismissDirection
                        val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
                        if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                            homeViewModel.deleteTransaction(transaction = transition)
                        }
                        val degrees by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default)
                                0f
                            else
                                -45f
                        )
                        var itemAppeared by remember {
                            mutableStateOf(false)
                        }
                        LaunchedEffect(key1 = true) {
                            itemAppeared = true
                        }
                        AnimatedVisibility(
                            visible = itemAppeared && !isDismissed,
                            enter = expandHorizontally(
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            ),
                            exit = shrinkHorizontally(
                                animationSpec = tween(
                                    durationMillis = 500
                                )
                            )
                        ) {
                            SwipeToDismiss(
                                state = dismissState,
                                //here directions means from which direction you want to show the background right to left or left to right
                                directions = setOf(DismissDirection.EndToStart),
                                //here fractional threshold means how much fraction of area you want to show while doing swipe to dismiss
                                dismissThresholds = { FractionalThreshold(fraction = 0.2f) },
                                //it is the background which will show up when you try to swipe
                                background = {
                                    if (dismissDirection == DismissDirection.EndToStart) {
                                        RedBackground(degrees = degrees)
                                    }
                                },
                                dismissContent = {

                                    TransactionItem(transaction = transition,
                                        currency = currency, onClick = {

                                            navController.navigate(route = "update_screen/${transition.id}")

                                        })

                                }
                            )
                        }

                    }
                    item {
                        Spacer(modifier = Modifier.height(74.dp))
                    }
                } else {
                    item {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillParentMaxHeight(0.4f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            NoItemScreen()
                        }
                    }
                }


            }
        }
    }


}

@Composable
fun AppTopBar(
    navController: NavController,
    title: String = ""
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(appPrimaryColor)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack, contentDescription = "back button",
            tint = Color.White,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, color = Color.White)

    }
}

@Composable
fun TransactionLayout(
    transaction: Transaction,
    onDeleteClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        contentAlignment = Alignment.Center
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = transaction.title)

                Text(text = transaction.amount)

                Text(text = transaction.time)

            }
            IconButton(onClick = {
                onDeleteClicked()
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }


    }
}