package com.phinion.studentexpensetracker.ui.screens.onboarding.model

import androidx.annotation.DrawableRes
import com.phinion.studentexpensetracker.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {

    object First: OnBoardingPage(
        image = R.drawable.easy_to_use_illustration,
        title = "Simple and Easy to use",
        description = "Purchased something? Paid something online\nKeep track of it by adding a transaction"
    )

    object Second: OnBoardingPage(
        image = R.drawable.saving_illustration,
        title = "Stay on track with budgets",
        description = "Create a budget and see how much you spent and reduce your spending to save money."
    )

}

