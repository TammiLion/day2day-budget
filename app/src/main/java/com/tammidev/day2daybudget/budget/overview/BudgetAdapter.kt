package com.tammidev.day2daybudget.budget.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.budget.Budget
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_budget.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import timber.log.Timber

class BudgetAdapter(var budgets: List<Budget>) : RecyclerView.Adapter<BudgetAdapter.BudgetVH>() {

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

    class BudgetVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        fun bind(budget: Budget) {
            name.text = budget.name
            amount.text = "%.2f".format(budget.totalAmount)
            spent.text = "%.2f".format(budget.amountSpent)
            val days: Double = (DateTime(budget.endDate - DateTime.now().millis).millis / DateTimeConstants.MILLIS_PER_DAY).toDouble()
            daysLeft.text = "%.0f".format(days)
            val allowance: String = "%.2f".format(((budget.totalAmount - budget.amountSpent) / (if (days <= 0) 1.0 else days)))
            Timber.d("calc: " + allowance)
            allowanceToday.text = allowance
            progressBar.progress = if (budget.amountSpent > 0) (budget.totalAmount / budget.amountSpent).toInt() else 0
        }
    }
}