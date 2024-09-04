package com.phinion.studentexpensetracker.ui.screens.onboarding

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.domain.models.Currency
import com.phinion.studentexpensetracker.domain.models.getCurrencyList
import com.phinion.studentexpensetracker.navigation.Screens
import com.phinion.studentexpensetracker.ui.screens.onboarding.viewmodel.WelcomeViewModel
import com.phinion.studentexpensetracker.ui.screens.transactions.add.DropDownCurrencyItem
import com.phinion.studentexpensetracker.ui.theme.*
import com.phinion.studentexpensetracker.ui.screens.onboarding.model.OnBoardingPage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(backgroundColor)
    systemUiController.setNavigationBarColor(backgroundColor)

    var nameQuery = remember {
        mutableStateOf("")
    }

    val pageList = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second
    )

    val dropDownMenuState = remember {
        mutableStateOf(false)
    }

    val dropDownCurrency = remember {
        mutableStateOf(
            Currency(
                icon = R.drawable.ic_cart,
                name = "Indian Rupee (INR)",
                currency = "₹"
            )
        )
    }


    val pagerState = rememberPagerState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        HorizontalPager(
            count = 3,
            modifier = Modifier.weight(8f),
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->

            if (page < 2) {

                PagerScreen(
                    onBoardingPage = pageList[page],
                    nameQuery = nameQuery,
                    pagerState = pagerState,
                    dropDownMenuState = dropDownMenuState,
                    dropDownCurrency = dropDownCurrency
                )

            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    OnBoardingContent(
                        pagerState = pagerState,
                        nameQuery = nameQuery,
                        dropDownCurrency = dropDownCurrency,
                        dropDownMenuState = dropDownMenuState
                    )

                }
            }

        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = buttonBackgroundColor,
            inactiveColor = Color.Gray,
            indicatorWidth = 12.dp,
            indicatorShape = RoundedCornerShape(16.dp),
            spacing = 8.dp
        )

        Column(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = pagerState.currentPage == 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {

                            if (nameQuery.value.isNotEmpty() && nameQuery.value.length >= 3) {
                                welcomeViewModel.saveOnBoardingState(completed = true)
                                welcomeViewModel.saveUsername(name = nameQuery.value)
                                welcomeViewModel.saveCurrency(dropDownCurrency.value.currency)
                                navController.popBackStack()
                                navController.navigate(Screens.HomeScreen.route)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Either the name field is empty or its should have at least 3 characters.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        },
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .clip(CircleShape)
                            .background(shape = CircleShape, color = onBoardingTextColor),
                        colors = ButtonDefaults.buttonColors(backgroundColor = onBoardingTextColor)
                    ) {
                        Text(
                            text = "Finish",
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


    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen(
    onBoardingPage: OnBoardingPage,
    nameQuery: MutableState<String>,
    pagerState: PagerState,
    dropDownCurrency: MutableState<Currency>,
    dropDownMenuState: MutableState<Boolean>
) {

    if (pagerState.currentPage != 2) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                modifier = Modifier
                    .fillMaxSize(0.65f),
                painter = painterResource(id = onBoardingPage.image),
                contentDescription = onBoardingPage.title
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = onBoardingPage.title,
                color = backgroundTextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = onBoardingPage.description,
                color = backgroundTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp)
            )

        }

    }


}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnBoardingContent(
    pagerState: PagerState,
    nameQuery: MutableState<String>,
    dropDownCurrency: MutableState<Currency>,
    dropDownMenuState: MutableState<Boolean>
) {


    //Button visible when we are at the on boarding page of 2
    AnimatedVisibility(
        visible = pagerState.currentPage == 2,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.Person, contentDescription = "person icon",
                modifier = Modifier.size(80.dp),
                tint = if (isSystemInDarkTheme()) Color.White else appPrimaryColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Let's get started with your name",
                color = backgroundTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = nameQuery.value, onValueChange = {
                    nameQuery.value = it
                },
                label = {
                    Text(
                        text = "Enter Your Name",
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

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = dropDownMenuState.value,
                onExpandedChange = { dropDownMenuState.value = it }) {
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

        }
    }


}