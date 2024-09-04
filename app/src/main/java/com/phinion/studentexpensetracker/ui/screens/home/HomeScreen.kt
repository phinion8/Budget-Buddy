package com.phinion.studentexpensetracker.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.ui.screens.home.viewmodel.HomeViewModel
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.ui.screens.components.dialogs.EditBudgetAlertDialog
import com.phinion.studentexpensetracker.ui.theme.*
import com.phinion.studentexpensetracker.utils.AppUtils
import com.phinion.studentexpensetracker.ui.screens.transactions.types.BudgetType
import com.phinion.studentexpensetracker.utils.RequestState
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClick: () -> Unit,
    navController: NavController
) {
    val wishState by remember {
        mutableStateOf(AppUtils.getGreetText())
    }

    val username by homeViewModel.username.collectAsState()

    val dailyBudget by homeViewModel.dailyBudget.collectAsState()

    var actualDailyBudget = if (dailyBudget == 0f) 1000f else dailyBudget


    val weeklyBudget by homeViewModel.weeklyBudget.collectAsState()

    val transactionList by homeViewModel.allTransaction.collectAsState()

    val currency by homeViewModel.currencyValue.collectAsState()

    var budgetType by remember {
        mutableStateOf(BudgetType.DAILY_BUDGET)
    }


    var dialogState by remember {
        mutableStateOf(false)
    }

    EditBudgetAlertDialog(
        dialogState = dialogState, onDismissRequest = {
            dialogState = !it
        },
        budgetType = budgetType,
        navController = navController
    )

    var todayOutcome = 0f
    var lastDayOutcome = 0f
    var last2DayOutcome = 0f
    var last3DayOutcome = 0f
    var last4DayOutcome = 0f
    var last5DayOutcome = 0f
    var last6DayOutcome = 0f
    var monthlyOutcome = 0f

    var weekDay1 = ""
    var weekDay2 = ""
    var weekDay3 = ""
    var weekDay4 = ""
    var weekDay5 = ""
    var weekDay6 = ""
    var weekDay7 = ""

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(backgroundColor)
    systemUiController.setNavigationBarColor(bottomBackgroundColor)


    var totalOutcome by remember {
        mutableStateOf(0f)
    }

    val todayDate = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("d-M-yyyy")
    val todayDateString = simpleDateFormat.format(todayDate)
    weekDay1 = SimpleDateFormat("EEE").format(todayDate)


    val calendar = Calendar.getInstance()
    calendar.time = todayDate
    calendar.add(Calendar.DATE, -1)
    val yesterdayAsString = simpleDateFormat.format(calendar.time)
    weekDay2 = SimpleDateFormat("EEE").format(calendar.time)

    calendar.time = todayDate
    calendar.add(Calendar.DATE, -2)
    val last2DayDate = simpleDateFormat.format(calendar.time)
    weekDay3 = SimpleDateFormat("EEE").format(calendar.time)

    calendar.time = todayDate
    calendar.add(Calendar.DATE, -3)
    val last3DayDate = simpleDateFormat.format(calendar.time)
    weekDay4 = SimpleDateFormat("EEE").format(calendar.time)


    calendar.time = todayDate
    calendar.add(Calendar.DATE, -4)
    val last4DayDate = simpleDateFormat.format(calendar.time)
    weekDay5 = SimpleDateFormat("EEE").format(calendar.time)

    calendar.time = todayDate
    calendar.add(Calendar.DATE, -5)
    val last5DayDate = simpleDateFormat.format(calendar.time)
    weekDay6 = SimpleDateFormat("EEE").format(calendar.time)

    calendar.time = todayDate
    calendar.add(Calendar.DATE, -6)
    val last6DayDate = simpleDateFormat.format(calendar.time)
    weekDay7 = SimpleDateFormat("EEE").format(calendar.time)



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {


        LazyColumn(contentPadding = PaddingValues(all = 4.dp)) {


            if (transactionList is RequestState.Success) {

                item {
                    HomeTopBar(onClick = onClick, userName = username, wish = wishState)
                }

                for (transaction in (transactionList as RequestState.Success<List<Transaction>>).data) {


                    if (transaction.time == todayDateString) {
                        todayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == yesterdayAsString) {
                        lastDayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == last2DayDate) {
                        last2DayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == last3DayDate) {
                        last3DayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == last4DayDate) {
                        last4DayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == last5DayDate) {
                        last5DayOutcome += transaction.amount.toFloat()

                    }
                    if (transaction.time == last6DayDate) {
                        last6DayOutcome += transaction.amount.toFloat()

                    }


                }



                item {

                    LaunchedEffect(key1 = true) {
                        totalOutcome =
                            todayOutcome + lastDayOutcome + last2DayOutcome + last3DayOutcome + last4DayOutcome
                        +last5DayOutcome + last6DayOutcome
                    }

                    OutcomeSection(
                        dailyBudget = actualDailyBudget.toInt(),
                        currency = currency,
                        expenses = totalOutcome,
                        todayOutcome = todayOutcome,
                        lastDayOutcome = lastDayOutcome,
                        last2dayOutcome = last2DayOutcome,
                        last3dayOutcome = last3DayOutcome,
                        last4dayOutcome = last4DayOutcome,
                        last5dayOutcome = last5DayOutcome,
                        last6dayOutcome = last6DayOutcome,
                        week1 = weekDay1,
                        week2 = weekDay2,
                        week3 = weekDay3,
                        week4 = weekDay4,
                        week5 = weekDay5,
                        week6 = weekDay6,
                        week7 = weekDay7,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Transactions",
                            color = backgroundTextColor,
                            fontFamily = poppins_semiBold
                        )
                        Text(
                            text = "See All",
                            color = backgroundTextColor,
                            modifier = Modifier.clickable {
                                navController.navigate("transaction_list_screen")
                            }, fontSize = 14.sp
                        )
                    }

                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }


                var count =
                    if ((transactionList as RequestState.Success<List<Transaction>>).data.size <= 3)
                        (transactionList as RequestState.Success<List<Transaction>>).data.size
                    else
                        3

                if ((transactionList as RequestState.Success<List<Transaction>>).data.isNotEmpty()) {

                    items(
                        items = (transactionList as RequestState.Success<List<Transaction>>).data.subList(
                            0,
                            count
                        ), key = {
                            it.id
                        }) { transition ->


                        var itemAppeared by remember {
                            mutableStateOf(false)
                        }
                        LaunchedEffect(key1 = true) {
                            itemAppeared = true
                        }
                        AnimatedVisibility(
                            visible = itemAppeared,
                            enter = expandHorizontally(
                                animationSpec = tween(
                                    durationMillis = 300
                                )
                            ),
                            exit = shrinkVertically(
                                animationSpec = tween(
                                    durationMillis = 300
                                )
                            )
                        ) {
                            if (itemAppeared) {
                                TransactionItem(
                                    transaction = transition,
                                    currency = currency,
                                    onClick = {

                                        navController.navigate(
                                            route = Screens.UpdateScreen.buildRoute(
                                                transition.id.toString()
                                            )
                                        )

                                    })
                            }

                        }

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

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {

                    DailyBudgetSection(
                        currentSpending = todayOutcome, onEditClicked = {

                            dialogState = true
                            budgetType = BudgetType.DAILY_BUDGET

                        }, maxBudget = dailyBudget,
                        budgetLeft = (dailyBudget - todayOutcome),
                        currency = currency,
                        isWeekly = false
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {

                    DailyBudgetSection(
                        currentSpending = totalOutcome, title = "Weekly Budget",
                        maxBudget = weeklyBudget.toFloat(),
                        onEditClicked = {

                            dialogState = true
                            budgetType = BudgetType.WEEKLY_BUDGET


                        },
                        budgetLeft = (weeklyBudget - totalOutcome).toFloat(),
                        currency = currency,
                        isWeekly = true
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(74.dp))
                }

            } else {
                item {
                    Column(
                        modifier = Modifier.fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(color = appLightColor)

                    }
                }
            }


        }


    }


}


@Composable
fun DailyBudgetSection(
    title: String = "Daily Budget",
    budgetLeft: Float = 0f,
    currentSpending: Float = 0f,
    maxBudget: Float = 1000f,
    onEditClicked: () -> Unit,
    currency: String,
    isWeekly: Boolean
) {


    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) currentSpending / maxBudget else 0f,
        animationSpec = tween(
            durationMillis = 2000, delayMillis = 0
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = title, fontFamily = poppins_semiBold, color = backgroundTextColor)

            Text(
                text = "Edit budget",
                color = backgroundTextColor,
                fontSize = 14.sp,
                modifier = Modifier.clickable {

                    onEditClicked()

                })

        }
        Spacer(modifier = Modifier.height(16.dp))

        if (maxBudget == 0f) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_budget),
                    contentDescription = "budget icon",
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "No Budget set!",
                    textAlign = TextAlign.Center, fontSize = 16.sp,
                    color = backgroundTextColor
                )


                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Set up a budget to help you stay on track with with your expenses",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp, color = Color.Gray
                )

            }
        } else {
            BudgetSection(
                budgetLeft = budgetLeft,
                currentSpending = currentSpending,
                maxBudget = maxBudget,
                curPercent = curPercent.value,
                currency = currency,
                isWeekly = isWeekly
            )
        }

    }

}


@Composable
fun BudgetSection(
    budgetLeft: Float = 0f,
    currentSpending: Float = 700f,
    maxBudget: Float = 1000f,
    curPercent: Float = 0f,
    currency: String,
    isWeekly: Boolean
) {

    val percentageExceeded by remember {

        derivedStateOf {

            (currentSpending / maxBudget) * 100

        }

    }


    Card(
        modifier = Modifier.fillMaxWidth(0.9f),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = outcomeSectionColor
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "Budget Left", color = Color.White, fontSize = 20.sp)
            Text(text = "$currency$budgetLeft", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .height(28.dp)
                    .background(Color.LightGray)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(curPercent)
                        .height(28.dp)
                        .clip(CircleShape)
                        .background(if (budgetLeft <= 0) Color.Red else Orange500)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .height(28.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$currency$currentSpending")
                    Text(text = "$currency$maxBudget")
                }


            }
            Spacer(modifier = Modifier.height(16.dp))



            if (percentageExceeded >= 100) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_warning),
                        contentDescription = "warning icon",
                        modifier = Modifier.size(35.dp),
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "You have over used your budget")
                }


            }

            if (isWeekly)
                Text(text = "You have used ${percentageExceeded.toInt()}% of your weekly budget")
            else
                Text(text = "You have used ${percentageExceeded.toInt()}% of your daily budget")

        }

    }
}


@Composable
fun HomeTopBar(
    userName: String = "Priyanshu",
    onClick: () -> Unit,
    wish: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = wish,
                fontSize = 16.sp,
                color = backgroundTextColor,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = userName,
                fontSize = 18.sp,
                color = backgroundTextColor,
                fontWeight = FontWeight.SemiBold
            )
        }

        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(
                    CircleShape
                )
                .border(width = 1.dp, color = backgroundTextColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = backgroundTextColor,
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        }

    }
}


@Composable
fun OutcomeSection(
    expenses: Float = 0f, currency: String = "₹",
    dailyBudget: Int = 1000,
    todayOutcome: Float = 0f,
    lastDayOutcome: Float = 0f,
    last2dayOutcome: Float = 0f,
    last3dayOutcome: Float = 0f,
    last4dayOutcome: Float = 0f,
    last5dayOutcome: Float = 0f,
    last6dayOutcome: Float = 0f,
    week1: String,
    week2: String,
    week3: String,
    week4: String,
    week5: String,
    week6: String,
    week7: String,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp)),
        elevation = 8.dp,
        backgroundColor = outcomeSectionColor
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Outcome", color = Color.White, fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(Last 7 days)", color = Color.White, fontSize = 12.sp
                )
            }

            Text(
                text = "$currency $expenses",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {


                GraphItem(
                    statValue = todayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week1
                )

                GraphItem(
                    statValue = lastDayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week2
                )

                GraphItem(
                    statValue = last2dayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week3
                )

                GraphItem(
                    statValue = last3dayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week4
                )

                GraphItem(
                    statValue = last4dayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week5
                )

                GraphItem(
                    statValue = last5dayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week6
                )

                GraphItem(
                    statValue = last6dayOutcome.toInt(),
                    statMaxValue = dailyBudget,
                    week = week7
                )


            }
        }


    }

}

@Composable
fun GraphItem(
    expenseValue: Float = 0f,
    height: Dp = 50.dp,
    week: String = "Sun",
    statValue: Int,
    statMaxValue: Int
) {


    var animationPlayed by remember {
        mutableStateOf(false)
    }


    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) statValue / statMaxValue.toFloat() else 0f,
        animationSpec = tween(
            durationMillis = 2000, delayMillis = 0
        )
    )



    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


    Column(
        modifier = Modifier
            .width(35.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .width(35.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Box(
                modifier = Modifier
                    .width(30.dp)
                    .fillMaxHeight(curPercent.value)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        color = if (statValue >= statMaxValue) Color.Red else Orange500,
                        shape = RoundedCornerShape(4.dp)
                    )

            )


        }
        Text(text = week, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.White)
    }


}

@Composable
fun TransactionItem(
    transaction: Transaction,
    currency: String,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = appLightColor, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = transaction.category.icon),
                    contentDescription = transaction.title,
                    tint = appPrimaryColor
                )
            }


            Column {
                Text(
                    text = transaction.title,
                    color = backgroundTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal

                )
                Text(text = transaction.category.name, color = Color.Gray, fontSize = 12.sp)
            }

            Column {
                Text(
                    text = "$currency${transaction.amount}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = backgroundTextColor
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = transaction.time, color = Color.Gray, fontSize = 12.sp,
                    textAlign = TextAlign.End,
                )

            }


        }

    }

}

@Composable
fun RedBackground(degrees: Float) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Default.Delete, contentDescription = "Delete Icon",
            tint = Color.White
        )
    }

}

@Composable
fun NoItemScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.no_transaction_icon),
            contentDescription = "no transaction icon",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "No Transactions!", color = backgroundTextColor, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tap + to add one.", color = Color.Gray, fontSize = 14.sp)
    }
}

