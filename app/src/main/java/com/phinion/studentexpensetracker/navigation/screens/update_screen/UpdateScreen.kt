package com.phinion.studentexpensetracker.navigation.screens.update_screen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.models.Transaction
import com.phinion.studentexpensetracker.models.getCategoryList
import com.phinion.studentexpensetracker.navigation.screens.transaction_screen.AppTopBar
import com.phinion.studentexpensetracker.navigation.screens.transaction_screen.DropDownItem
import com.phinion.studentexpensetracker.ui.theme.*
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateScreen(
    navController: NavController,
    updateScreenViewModel: UpdateScreenViewModel,
    transaction: Transaction
) {

    val context = LocalContext.current



    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(appPrimaryColor)

    BackHandler(enabled = true) {
        navController.popBackStack()
    }

    var transactionTitle by remember {
        mutableStateOf("")
    }

    transactionTitle = transaction.title



    var updatedTransaction by remember {
        mutableStateOf(transaction)
    }


    var titleQuery by remember {
        mutableStateOf("${updatedTransaction.title}")
    }


    var noteQuery by remember {
        mutableStateOf("${updatedTransaction.note}")
    }

    var amountQuery by remember {
        mutableStateOf("${updatedTransaction.amount}")
    }

    var dropDownMenuState by remember {
        mutableStateOf(false)
    }

    var dropDownCategory by remember {
        mutableStateOf(updatedTransaction.category)
    }


    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    var currentDate by remember {
        mutableStateOf("${updatedTransaction.time}")
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            currentDate = "$mDayOfMonth-${mMonth + 1}-$mYear"
        }, currentYear, currentMonth, currentDay
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {


        AppTopBar(navController = navController, title = "Update '${transactionTitle}' ")


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
                    titleQuery = it
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
                    noteQuery = it
                },
                label = {
                    Text(
                        text = "Note",
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
                        amountQuery = it
                    } else {
                        amountQuery = when (it.toDoubleOrNull()) {
                            null -> amountQuery //old value
                            else -> it   //new value
                        }
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
                            dropDownCategory = category
                        }) {

                            DropDownItem(category = category)

                        }

                    }

                }
            }




            Button(
                onClick = {

                    if (titleQuery.isNotEmpty() && noteQuery.isNotEmpty() && amountQuery
                            .isNotEmpty() && amountQuery != 0.toString()
                    ) {
                        updateScreenViewModel.updateSelectedTransaction(
                            Transaction(
                                id = updatedTransaction.id,
                                title = titleQuery,
                                note = noteQuery,
                                amount = amountQuery,
                                time = currentDate,
                                category = dropDownCategory
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
                        Toast.makeText(context, "Please fill out out the fields.", Toast.LENGTH_SHORT)
                            .show()
                    }


                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(shape = RoundedCornerShape(8.dp), color = buttonBackgroundColor),
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