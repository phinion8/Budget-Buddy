package com.phinion.studentexpensetracker.ui.screens.profile

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.ui.screens.home.viewmodel.HomeViewModel
import com.phinion.studentexpensetracker.domain.models.Currency
import com.phinion.studentexpensetracker.domain.models.getCurrencyList
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.ui.screens.transactions.list.AppTopBar
import com.phinion.studentexpensetracker.ui.screens.transactions.add.DropDownCurrencyItem
import com.phinion.studentexpensetracker.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(appPrimaryColor)

    val username by homeViewModel.username.collectAsState()

    var nameQuery = remember {
        mutableStateOf("$username")
    }

    BackHandler(enabled = true) {
        navController.popBackStack()
    }


    var dropDownMenuState = remember {
        mutableStateOf(false)
    }

    var dropDownCurrency = remember {
        mutableStateOf(Currency(icon = R.drawable.ic_cart, name = "Indian Rupee\n(INR)", currency = "₹"))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(navController = navController, title = "Profile")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(
                        CircleShape
                    )
                    .border(width = 1.dp, color = backgroundTextColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "search icon",
                    tint = backgroundTextColor,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = username, textAlign = TextAlign.Center)


            Spacer(modifier = Modifier.height(24.dp))

            //Update Name
            TextField(
                value = nameQuery.value, onValueChange = {
                    nameQuery.value = it
                },
                label = {
                    Text(
                        text = "Update Your Name",
                        fontFamily = poppins_regular,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
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

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = dropDownMenuState.value,
                onExpandedChange = { dropDownMenuState.value = it }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
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
                        DropDownCurrencyItem(currency = dropDownCurrency.value)
                    }
                }
                ExposedDropdownMenu(expanded = dropDownMenuState.value, onDismissRequest = {
                    dropDownMenuState.value = false
                }) {

                    getCurrencyList().forEach { currency ->

                        DropdownMenuItem(onClick = {
                            dropDownMenuState.value = false
                            dropDownCurrency.value = currency
                        }) {

                            DropDownCurrencyItem(currency = currency)

                        }

                    }

                }
            }


            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    if (nameQuery.value.isNotEmpty() && nameQuery.value.length >= 3) {

                        homeViewModel.saveUsername(name = nameQuery.value)
                        homeViewModel.saveCurrency(currency = dropDownCurrency.value.currency)

                        navController.navigate(Screens.HomeScreen.route)
                        Toast.makeText(
                            context,
                            "Successfully updated the information.",
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
                    text = "Update",
                    color = Color.White,
                    modifier = Modifier.padding(all = 4.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = poppins_semiBold
                )
            }

            Spacer(modifier = Modifier.height(56.dp))

        }
    }
}