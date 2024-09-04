package com.phinion.studentexpensetracker.ui.screens.transactions.update

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.domain.models.getCategoryList
import com.phinion.studentexpensetracker.ui.screens.transactions.update.viewmodel.UpdateScreenViewModel
import com.phinion.studentexpensetracker.ui.screens.transactions.list.AppTopBar
import com.phinion.studentexpensetracker.ui.screens.transactions.add.DropDownItem
import com.phinion.studentexpensetracker.ui.theme.*
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateScreen(
    navController: NavController,
    viewModel: UpdateScreenViewModel,
    transactionId: String
) {

    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(appPrimaryColor)

    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val titleQuery by viewModel.titleQuery

    val noteQuery by viewModel.noteQuery

    val amountQuery by viewModel.amountQuery

    var dropDownMenuState by remember {
        mutableStateOf(false)
    }

    val dropDownCategory by viewModel.dropDownCategory

    val currentDate by viewModel.currentDate

    val timeInLong by viewModel.timeInLong


    LaunchedEffect(
        key1 = transactionId
    ) {

        viewModel.getSelectedTransaction(transactionId = transactionId.toInt())

    }

    BackHandler(enabled = true) {
        navController.popBackStack()
    }


    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.updateCurrentDate("$mDayOfMonth-${mMonth + 1}-$mYear")
            viewModel.updateTimeInLong(Date(mYear, mMonth, mDayOfMonth).time)
        }, currentYear, currentMonth, currentDay
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {


        AppTopBar(
            navController = navController,
            title = "Update '${titleQuery}'"
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select Date",
                fontSize = 16.sp,
                color = backgroundTextColor,
                textAlign = TextAlign.Center
            )

            Row {

                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_calendar),
                    contentDescription = "calendar",
                    tint = backgroundTextColor
                )

                Spacer(modifier = Modifier.width(8.dp))


                Text(modifier = Modifier.clickable {
                    datePickerDialog.show()
                }, text = currentDate)

            }


            TextField(
                value = titleQuery, onValueChange = {
                    viewModel.updateTitleQuery(it)

                },
                label = {
                    Text(
                        text = "Title",
                        fontFamily = poppins_regular,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp)
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = backgroundTextColor
                    ),
                singleLine = true,
                textStyle = TextStyle(fontFamily = poppins_regular, fontSize = 14.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    leadingIconColor = backgroundTextColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedLabelColor = backgroundTextColor,
                    cursorColor = backgroundTextColor
                )
            )

            TextField(
                value = noteQuery, onValueChange = {
                    viewModel.updateNoteQuery(it)
                },
                label = {
                    Text(
                        text = "Note(Optional)",
                        fontFamily = poppins_regular,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp)
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = backgroundTextColor
                    ),
                singleLine = true,
                textStyle = TextStyle(fontFamily = poppins_regular, fontSize = 14.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    leadingIconColor = backgroundTextColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedLabelColor = backgroundTextColor,
                    cursorColor = backgroundTextColor
                )
            )

            TextField(
                value = amountQuery, onValueChange = {
                    if (it.isEmpty()) {
                        viewModel.updateAmountQuery(it)
                    } else {
                        val amountValue = when (it.toDoubleOrNull()) {
                            null -> amountQuery //old value
                            else -> it   //new value
                        }
                        viewModel.updateAmountQuery(amountValue)
                    }

                },
                label = {
                    Text(
                        text = "Amount",
                        fontFamily = poppins_regular,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(55.dp)
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = backgroundTextColor
                    ),
                singleLine = true,
                textStyle = TextStyle(fontFamily = poppins_regular, fontSize = 14.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    leadingIconColor = backgroundTextColor,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    focusedLabelColor = backgroundTextColor,
                    cursorColor = backgroundTextColor
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )


            ExposedDropdownMenuBox(
                expanded = dropDownMenuState,
                onExpandedChange = { dropDownMenuState = it }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp)
                        .border(
                            shape = RoundedCornerShape(8.dp),
                            color = backgroundTextColor,
                            width = 2.dp
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DropDownItem(category = dropDownCategory)
                    }
                }
                ExposedDropdownMenu(expanded = dropDownMenuState, onDismissRequest = {
                    dropDownMenuState = false
                }) {

                    getCategoryList().forEach { category ->

                        DropdownMenuItem(onClick = {
                            dropDownMenuState = false
                            viewModel.updateDropDownCategory(category)
                        }) {

                            DropDownItem(category = category)

                        }

                    }

                }
            }




            Button(
                onClick = {

                    if (titleQuery.isNotEmpty() && amountQuery
                            .isNotEmpty() && amountQuery != 0.toString()
                    ) {
                        viewModel.updateSelectedTransaction(
                            Transaction(
                                id = transactionId.toInt(),
                                title = titleQuery,
                                note = noteQuery,
                                amount = amountQuery,
                                time = currentDate,
                                category = dropDownCategory,
                                timeInLong = timeInLong
                            )
                        )
                        navController.popBackStack()
                        Toast.makeText(
                            context,
                            "Successfully added the transaction.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill out out the fields.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }


                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        shape = RoundedCornerShape(8.dp),
                        color = buttonBackgroundColor
                    ),
                colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackgroundColor)
            ) {
                Text(
                    text = "Update Transaction",
                    color = Color.White,
                    modifier = Modifier.padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = poppins_semiBold
                )
            }

        }


    }


}