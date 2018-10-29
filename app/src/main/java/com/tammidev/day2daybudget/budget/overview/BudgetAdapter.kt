package com.tammidev.day2daybudget.budget.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.budget.Budget
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_budget.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import timber.log.Timber

class BudgetAdapter(private var budgets: List<Budget>, val editEvents: PublishSubject<Int>, val deleteEvents: PublishSubject<Int>) : RecyclerView.Adapter<BudgetAdapter.BudgetVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetVH {
        return BudgetVH(LayoutInflater.from(parent.context).inflate(R.layout.item_budget, parent, false))
    }

    override fun getItemCount(): Int {
        return budgets.size
    }

    override fun onBindViewHolder(holder: BudgetVH, position: Int) {
        holder.bind(budgets[position])
    }

    fun budgetsRefreshed(budgets: List<Budget>?) {
        budgets?.let {
            this.budgets = budgets
            notifyDataSetChanged()
        }
    }

    //TODO move all calculations to helper class
    inner class BudgetVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(budget: Budget) {
            name.text = budget.name
            amount.text = "%.2f".format(budget.totalAmount)
            spent.text = "%.2f".format(budget.amountSpent)
            val days: Double = (DateTime(budget.endDate - DateTime.now().millis).millis / DateTimeConstants.MILLIS_PER_DAY).toDouble()
            daysLeft.text = "%.0f".format(days)
            val allowanceTotal = (budget.totalAmount - budget.amountSpent) / (if (days <= 0) 1.0 else days)
            val allowance: String = "w %.2f | w/o %.2f".format(getAllowancePerDayIncludingToday(budget), getAllowancePerDayExcludingToday(budget))
            allowanceEachDay.text = allowance
            allowanceToday.text = getAllowanceToday(allowanceTotal, budget)
            progressBar.progress = if (budget.amountSpent > 0) (budget.totalAmount / budget.amountSpent).toInt() else 0
            editBtn.setOnClickListener { editEvents.onNext(budget.id) }
            deleteBtn.setOnClickListener { deleteEvents.onNext(budget.id) }
        }

        private fun getAllowancePerDayIncludingToday(budget: Budget): Double {
            val days: Double = (DateTime(budget.endDate - DateTime.now().millis).millis / DateTimeConstants.MILLIS_PER_DAY).toDouble()
            val allowanceTotal = (budget.totalAmount - budget.amountSpent) / (if (days <= 0) 1.0 else days)
            return allowanceTotal
        }

        private fun getAllowancePerDayExcludingToday(budget: Budget): Double {
            val days: Double = (DateTime(budget.endDate - DateTime.now().millis).millis / DateTimeConstants.MILLIS_PER_DAY).toDouble()
            val startOfToday = DateTime.now().withTimeAtStartOfDay()
            val endOfToday = DateTime.now().plusDays(1).withTimeAtStartOfDay()

            val expensesToday = budget.expenses.filter {
                val expenseData = DateTime(it.date)
                expenseData.isAfter(startOfToday) && expenseData.isBefore(endOfToday)
            }.map { it.cost }.sum()
            val allowanceTotal = (budget.totalAmount - (budget.amountSpent - expensesToday)) / (if (days <= 0) 1.0 else days)
            return allowanceTotal
        }


        private fun getAllowanceToday(allowanceTotal: Double, budget: Budget): CharSequence? {
            val startOfToday = DateTime.now().withTimeAtStartOfDay()
            val endOfToday = DateTime.now().plusDays(1).withTimeAtStartOfDay()

            val expenses = budget.expenses.filter {
                val expenseData = DateTime(it.date)
                expenseData.isAfter(startOfToday) && expenseData.isBefore(endOfToday)
            }.map { it.cost }

            expenses.forEach { Timber.d("Today expense: " + it) }
            val allowedToday = allowanceTotal - expenses.sum()
            Timber.d("Allowed today: " + allowedToday.toString())

            return "%.2f".format(allowedToday)
        }
    }
}