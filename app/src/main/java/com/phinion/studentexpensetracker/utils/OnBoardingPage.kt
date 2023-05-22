package com.phinion.studentexpensetracker.utils

import androidx.annotation.DrawableRes
import com.phinion.studentexpensetracker.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {

    object First: OnBoardingPage(
        image = R.drawable.user_interface_image,
        title = "Simple and Easy to use",
        description = "Purchased something? Paid something online\nKeep track of it by adding a transaction"
    )

    object Second: OnBoardingPage(
        image = R.drawable.budget_image,
        title = "Stay on track with budgets",
        description = "Create a budget and see how much you spent."
    )

    object Third: OnBoardingPage(
        image = R.drawable.ic_budget,
        title = "Some Title",
        description = "Some description"
    )

}

