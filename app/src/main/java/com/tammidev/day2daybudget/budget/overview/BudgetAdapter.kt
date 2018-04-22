package com.tammidev.day2daybudget.budget.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.budget.Budget
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_budget.*

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
            amount.text = budget.totalAmount.toString()
            spent.text = budget.amountSpent.toString()
            progressBar.progress = if (budget.amountSpent > 0) (budget.totalAmount / budget.amountSpent).toInt() else 0
        }
    }
}