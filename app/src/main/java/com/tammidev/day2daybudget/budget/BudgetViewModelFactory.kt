package com.tammidev.day2daybudget.budget

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tammidev.day2daybudget.budget.configure.ConfigureViewModel
import com.tammidev.day2daybudget.budget.overview.OverviewViewModel
import javax.inject.Inject


class BudgetViewModelFactory @Inject constructor(private val budgetRepo: BudgetRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(OverviewViewModel::class.java) -> return OverviewViewModel(budgetRepo) as T
            modelClass.isAssignableFrom(ConfigureViewModel::class.java) -> return ConfigureViewModel(budgetRepo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}