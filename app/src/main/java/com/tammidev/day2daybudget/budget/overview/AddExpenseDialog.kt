package com.tammidev.day2daybudget.budget.overview

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.GridLayout
import android.widget.Toast
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.BudgetRepo
import com.tammidev.day2daybudget.budget.Expense
import com.tammidev.day2daybudget.utils.adapters.TextWatcherAdapter
import kotlinx.android.synthetic.main.dialog_add_expense.*
import javax.inject.Inject

private const val EXTRA_BUDGET_ID = "budget_id"

class AddExpenseDialog : DialogFragment() {

    @Inject
    lateinit var budgetRepo: BudgetRepo

    private var amount: Double = 0.00
    private val tags: MutableList<String> = ArrayList()
    private val adapter = TagAdapter(tags)

    companion object {
        fun newInstance(budgetId: Int): AddExpenseDialog {
            val bundle = Bundle()
            bundle.putInt(EXTRA_BUDGET_ID, budgetId)
            val dialog = AddExpenseDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val app = context!!.applicationContext as D2dApp
        app.getComponent().inject(this)

        val layoutManager = StaggeredGridLayoutManager(4, GridLayout.VERTICAL)
        tagsRecyclerView.layoutManager = layoutManager
        tagsRecyclerView.adapter = adapter

        tagsEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                val value = tagsEditText.text.toString()
                if (value.isNotEmpty()) {
                    tags.add(value)
                    adapter.tagsRefreshed(tags)
                }
                tagsEditText.text.clear()
                true
            } else {
                false
            }
        }

        expenseEditText.addTextChangedListener(object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.toString().isNullOrEmpty()) {
                    amount = 0.00
                } else {
                    amount = s.toString().toDouble()
                }
            }
        })

        saveBtn.setOnClickListener {
            if (amount > 0.00) {
                saveExpense()
            } else {
                Toast.makeText(context, "Amount needs to be greater than 0", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveExpense() {
        val id = arguments!!.getInt(EXTRA_BUDGET_ID)
        val livedata = budgetRepo.get(id)
        livedata.observe(this, Observer {
            if (it?.isNotEmpty() == true) {
                if (it.size > 1) {
                    Log.w("AddExpenseDialog", "Repo retrieved more than one budget for a budgetId.")
                }
                val budget = it[0]
                budget.expenses.add(Expense(cost = amount, tags = tags))
                budget.amountSpent += amount
                livedata.removeObservers(this)
                budgetRepo.update(budget)
                this.dismiss()
            }
        })
    }
}