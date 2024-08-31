package com.phinion.studentexpensetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phinion.studentexpensetracker.R
import com.phinion.studentexpensetracker.utils.Constants.TRANSACTION_TABLE

@Entity(tableName = TRANSACTION_TABLE)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val amount: String,
    val time: String,
    val category: Category = Category(R.drawable.ic_cart, "Unknown"),
    val timeInLong: Long
)
