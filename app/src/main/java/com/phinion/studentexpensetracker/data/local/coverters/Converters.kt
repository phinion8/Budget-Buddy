package com.phinion.studentexpensetracker.data.local.coverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phinion.studentexpensetracker.domain.models.Category

class Converters {

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