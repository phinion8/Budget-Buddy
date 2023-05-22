package com.phinion.studentexpensetracker.data.local.transaction_database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phinion.studentexpensetracker.models.Category
import java.util.*

class DatabaseConverter {

    @TypeConverter
    fun categoryToString(category: Category): String {
        return Gson().toJson(category)
    }

    @TypeConverter
    fun stringToCategory(data: String): Category {
        val categoryType = object : TypeToken<Category>() {}.type
        return Gson().fromJson(data, categoryType)
    }
}