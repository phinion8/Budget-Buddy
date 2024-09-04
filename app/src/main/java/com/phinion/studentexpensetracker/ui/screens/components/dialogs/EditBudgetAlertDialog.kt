package com.phinion.studentexpensetracker.ui.screens.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.ui.screens.home.viewmodel.HomeViewModel
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.ui.theme.*
import com.phinion.studentexpensetracker.ui.screens.transactions.types.BudgetType

@Composable
fun EditBudgetAlertDialog(
    dialogState: Boolean,
    onDismissRequest: (dialogState: Boolean) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    budgetType: BudgetType,
    navController: NavController
) {

    var textQuery by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    if (dialogState) {
        Dialog(
            onDismissRequest = { onDismissRequest(dialogState) },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            content = {

                Card(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 16.dp,
                    backgroundColor = backgroundColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {


                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(shape = CircleShape, color = buttonBackgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_budget),
                                contentDescription = "budget",
                                modifier = Modifier.size(60.dp)
                            )
                        }

                        Text(text = "Edit Budget")

                        TextField(
                            value = textQuery, onValueChange = {

                                if (it.isEmpty()){
                                    textQuery = it
                                }else{
                                    textQuery = when (it.toDoubleOrNull()) {
                                        null -> textQuery //old value
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
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                focusedLabelColor = backgroundTextColor,
                                cursorColor = backgroundTextColor
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Button(
                            onClick = {
                                if (textQuery.isNotEmpty()){
                                    when(budgetType){
                                        BudgetType.DAILY_BUDGET -> homeViewModel.saveDailyBudget(amount = textQuery.toFloat())
                                        BudgetType.WEEKLY_BUDGET -> homeViewModel.saveWeeklyBudget(amount = textQuery.toDouble())
                                    }
                                    navController.navigate(Screens.HomeScreen.route){
                                        popUpTo(Screens.HomeScreen.route){
                                            inclusive = true
                                        }
                                    }

                                }else{
                                    Toast.makeText(context, "Budget can not be empty.", Toast.LENGTH_SHORT).show()
                                }


                            }, modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    color = buttonBackgroundColor
                                ),
                            colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackgroundColor)
                        ) {

                            Text(
                                text = "Save",
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
        )
    }


}