package com.tammidev.day2daybudget.budget

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExpenseListConverter {

    @TypeConverter
    fun fromGsonString(value: String?): List<Expense> {
        val listType = object : TypeToken<List<Expense>>() {}.type
        return if (value.isNullOrEmpty()) ArrayList() else Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listToGsonString(expenses: List<Expense>): String {
        return Gson().toJson(expenses)
    }
}