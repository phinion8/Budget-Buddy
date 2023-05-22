package com.phinion.studentexpensetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.phinion.studentexpensetracker.utils.Constants.USER_DATA_TABLE

@Entity(tableName = USER_DATA_TABLE)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val balance: Long
)
