package com.tammidev.day2daybudget.budget

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {

    @TypeConverter
    fun fromGsonString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return if (value == null) null else Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToGsonString(expenses: List<String>): String {
        return Gson().toJson(expenses)
    }
}