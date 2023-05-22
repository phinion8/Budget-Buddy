package com.phinion.studentexpensetracker.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.phinion.studentexpensetracker.data.viewmodels.HomeViewModel
import com.phinion.studentexpensetracker.models.User


@Composable
fun AddUserScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val userNameQuery by homeViewModel.userNameQuery
    val userBalanceQuery by homeViewModel.userBalanceQuery



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        TextField(value = userNameQuery, onValueChange = {
            homeViewModel.updateUserNameQuery(it)
        },
            placeholder = {
                Text(text = "Name")
            })

        TextField(value = userBalanceQuery.toString(), onValueChange = {
            homeViewModel.updateUserBalanceQuery(it.toFloat())
        }, placeholder = {
            Text(text = "Balance")
        })

        Button(onClick = {
            homeViewModel.addUserDetails(user = User(
                name = userNameQuery, balance = userBalanceQuery.toLong()
            ))
        }) {

            Text(text = "Add")

        }


    }


}