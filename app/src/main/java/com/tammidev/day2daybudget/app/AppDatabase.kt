package com.tammidev.day2daybudget.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetDao
import com.tammidev.day2daybudget.budget.ExpenseListConverter
import com.tammidev.day2daybudget.budget.StringListConverter

@Database(entities = [(Budget::class)], version = 3)
@TypeConverters(ExpenseListConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}