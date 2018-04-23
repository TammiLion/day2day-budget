package com.tammidev.day2daybudget.budget.configure

import android.arch.lifecycle.ViewModel
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import org.joda.time.DateTime
import javax.inject.Inject

/**
 * Created by troep on 8/27/17.
 */
class ConfigureViewModel @Inject constructor(private val budgetRepo: BudgetRepo) : ViewModel() {
    internal var budgetInEditing: Budget = Budget()

    fun amountChanged(enteredTotalAmount: String) {
        val amount = enteredTotalAmount.toDoubleOrNull()
        amount?.let {
            budgetInEditing.totalAmount = amount
        }
    }

    fun nameChanged(name: String) {
        if (name.isNotEmpty()) budgetInEditing.name = name
    }

    fun daysPicked(days: Int) {
        budgetInEditing.endDate = DateTime.now().plusDays(days).millis
    }


    fun repeatSwitchToggled(repeat: Boolean) {
        budgetInEditing.repeat = repeat
    }

    fun save() {
        budgetRepo.save(budgetInEditing)
    }
}