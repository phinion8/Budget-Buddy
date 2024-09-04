package com.phinion.studentexpensetracker.domain.models

import com.phinion.studentexpensetracker.R


data class Category(
    val icon: Int,
    val name: String
)

fun getCategoryList(): List<Category>{
    return listOf(
        Category(R.drawable.ic_food, "Food and Dining"),
        Category(R.drawable.ic_cart, "Shopping"),
        Category(R.drawable.ic_travel, "Travelling"),
        Category(R.drawable.ic_medical, "Medical"),
        Category(R.drawable.ic_personal_care, "Personal Care"),
        Category(R.drawable.ic_education, "Education"),
        Category(R.drawable.ic_bills, "Bills and Utilities"),
        Category(R.drawable.ic_investments, "Investments"),
        Category(R.drawable.ic_rent, "Rent"),
        Category(R.drawable.ic_tax, "Taxes"),
        Category(R.drawable.ic_insurance, "Insurance"),
        Category(R.drawable.ic_gift, "Gift and Donation")
    )
}
