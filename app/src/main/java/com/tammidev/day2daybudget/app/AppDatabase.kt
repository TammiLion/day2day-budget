package com.tammidev.day2daybudget.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetDao

@Database(entities = arrayOf(Budget::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}