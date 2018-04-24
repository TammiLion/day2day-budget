package com.tammidev.day2daybudget.budget.configure

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConfigureViewModelTest {

    lateinit var viewModel: ConfigureViewModel
    lateinit var repo: BudgetRepo

    val budget = Budget(name = "test", totalAmount = 10.0, repeat = true)

    @Before
    fun setup() {
        repo = mock()
        viewModel = ConfigureViewModel(repo)
    }

    @Test
    fun test() {
        viewModel.amountChanged(budget.totalAmount.toString())
        viewModel.nameChanged(budget.name)
        viewModel.repeatSwitchToggled(true)

        assert(viewModel.budgetInEditing.name == budget.name)
        assert(viewModel.budgetInEditing.repeat == budget.repeat)
        assert(viewModel.budgetInEditing.totalAmount == budget.totalAmount)

        Mockito.doNothing().`when`(repo).save(viewModel.budgetInEditing)
        viewModel.save()
        Mockito.verify(repo, times(1)).save(viewModel.budgetInEditing)
    }
}