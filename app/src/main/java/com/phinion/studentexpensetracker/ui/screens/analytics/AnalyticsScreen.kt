package com.phinion.studentexpensetracker.ui.screens.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.phinion.studentexpensetracker.ui.screens.home.viewmodel.HomeViewModel
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.ui.screens.components.dialogs.EditBudgetAlertDialog
import com.phinion.studentexpensetracker.ui.screens.home.DailyBudgetSection
import com.phinion.studentexpensetracker.ui.theme.appPrimaryColor
import com.phinion.studentexpensetracker.ui.screens.transactions.types.BudgetType
import com.phinion.studentexpensetracker.utils.RequestState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AnalyticsScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val allTransaction by homeViewModel.allTransaction.collectAsState()

    val dailyBudget by homeViewModel.dailyBudget.collectAsState()

    val weeklyBudget by homeViewModel.weeklyBudget.collectAsState()

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


    if (allTransaction is RequestState.Success) {
        for (transaction in (allTransaction as RequestState.Success<List<Transaction>>).data) {
            LaunchedEffect(key1 = true) {
                totalOutcome += transaction.amount.toFloat()
            }
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

        var weeklyOutcome by remember {
            mutableStateOf(0f)
        }


            weeklyOutcome =
                todayOutcome + lastDayOutcome + last2DayOutcome + last3DayOutcome + last4DayOutcome
            +last5DayOutcome + last6DayOutcome


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnalyticTopSection(
                totalOutcome = totalOutcome,
                currency = currency
            )
            //Daily budget section
            DailyBudgetSection(
                currentSpending = todayOutcome,
                onEditClicked = {

                    dialogState = true
                    budgetType = BudgetType.DAILY_BUDGET

                },
                maxBudget = dailyBudget,
                budgetLeft = (dailyBudget - todayOutcome),
                currency = currency,
                isWeekly = false
            )

            //Weekly budget section
            DailyBudgetSection(
                currentSpending = weeklyOutcome, title = "Weekly Budget",
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

    }


}

@Composable
fun AnalyticTopSection(
    totalOutcome: Float,
    currency: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp)), elevation = 8.dp, backgroundColor = appPrimaryColor
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp)
        ) {

            Text(
                text = "Total Outcome", color = Color.White, fontSize = 18.sp
            )

            Text(
                text = "$currency $totalOutcome",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

        }


    }
}

