package com.tammidev.day2daybudget.budget.overview

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

const val BUDGET_ID = 27

@RunWith(MockitoJUnitRunner::class)
class OverviewViewModelTest {

    lateinit var viewModel: OverviewViewModel
    lateinit var repo: BudgetRepo

    val budgetWithId = Budget().apply { id = BUDGET_ID }
    val budgetList = listOf(budgetWithId, Budget("anotherOne"), Budget("lastOne"))
    val liveData: LiveData<List<Budget>> = object : LiveData<List<Budget>>() {
        override fun observe(owner: LifecycleOwner, observer: Observer<List<Budget>>) {
            super.observe(owner, observer)
            observer.onChanged(budgetList)
        }
    }

    @Before
    fun setup() {
        repo = mock()
        doReturn(liveData).`when`(repo).getAll()
        viewModel = OverviewViewModel(repo)
    }

    @Test
    fun test() {
        assert(viewModel.getBudgetId(0) == BUDGET_ID)
    }
}