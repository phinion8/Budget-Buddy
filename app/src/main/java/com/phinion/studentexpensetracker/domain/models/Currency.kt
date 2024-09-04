package com.phinion.studentexpensetracker.domain.models

import androidx.annotation.DrawableRes
import com.phinion.studentexpensetracker.R

data class Currency(
    @DrawableRes
    val icon: Int,
    val name: String,
    val currency: String
)

fun getCurrencyList(): List<Currency>{
    return listOf(
        Currency(
            icon = R.drawable.ic_ruppee,
            name = "Indian Rupee\n(INR)",
            currency = "₹"
        ),
        Currency(
            icon = R.drawable.ic_dollar,
            name = "United States Dollar (USD)",
            currency = "$"
        ),
        Currency(
            icon = R.drawable.ic_euro,
            name = "European Euro\n(EUR)",
            currency = "$"
        ),
        Currency(
            icon = R.drawable.ic_yen,
            name = "Japanese Yen\n(YEN)",
            currency = "¥"
        )
    )
}
