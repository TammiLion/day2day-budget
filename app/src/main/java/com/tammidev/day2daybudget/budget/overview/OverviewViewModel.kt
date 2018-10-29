package com.tammidev.day2daybudget.budget.overview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by troep on 8/27/17.
 */
class OverviewViewModel @Inject constructor(val budgetRepo: BudgetRepo) : ViewModel() {

    var budgets: LiveData<List<Budget>> = budgetRepo.getAll()

    fun clicked(position: Int) {
        Timber.d("clicked: " + budgets.value?.get(position).toString())
    }

    fun longClicked(position: Int) {
        Timber.d("longClicked: " + budgets.value?.get(position).toString())
    }

    fun getBudgetId(position: Int): Int? {
        return budgets.value?.get(position)?.id
    }

    fun requestToEdit(it: Int) {

    }

    fun requestToDelete(id: Int) {
        budgetRepo.delete(id)
    }
}