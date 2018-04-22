package com.tammidev.day2daybudget.budget.overview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import javax.inject.Inject

/**
 * Created by troep on 8/27/17.
 */
class OverviewViewModel @Inject constructor(budgetRepo: BudgetRepo) : ViewModel() {

    val budgets: LiveData<List<Budget>> = budgetRepo.getAll()

}