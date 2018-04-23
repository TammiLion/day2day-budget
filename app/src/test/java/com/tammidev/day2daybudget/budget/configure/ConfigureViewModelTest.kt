package com.tammidev.day2daybudget.budget.configure

import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfigureViewModelTest {

    lateinit var viewModel: ConfigureViewModel
    lateinit var repo: BudgetRepo

    val budget = Budget(name = "test", totalAmount = 10.0, repeat = true)

    fun <T> any() = Mockito.any<T>() as T
    //fun <T> any(type: Class<T>) = Mockito.any<T>() as T

    @Before
    fun setup() {
        repo = Mockito.mock(BudgetRepo::class.java)
        viewModel = ConfigureViewModel(repo)
        //`when`(repo.save(any())).then { doNothing() }
    }

    @Test
    fun test() {
        viewModel.amountChanged(budget.totalAmount.toString())
        viewModel.daysPicked(2)
        viewModel.nameChanged(budget.name)
        viewModel.repeatSwitchToggled(true)
        assert(viewModel.budgetInEditing.name == budget.name)
        assert(viewModel.budgetInEditing.repeat == budget.repeat)
        assert(viewModel.budgetInEditing.totalAmount == budget.totalAmount)
        //Mockito.verify(repo, times(1)).save(any())
    }
}