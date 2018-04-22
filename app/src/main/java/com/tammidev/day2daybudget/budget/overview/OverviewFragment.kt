package com.tammidev.day2daybudget.budget.overview

import ItemClickSupport
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.Budget
import com.tammidev.day2daybudget.budget.BudgetViewModelFactory
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

const val SPAN_COUNT = 2

class OverviewFragment : Fragment() {

    private val adapter: BudgetAdapter = BudgetAdapter(ArrayList<Budget>())

    companion object {
        private val SOME_KEY = "some_key";
    }

    @Inject
    lateinit var viewModelFactory: BudgetViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(OverviewViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context?.applicationContext as D2dApp).getComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        startObservingViewModel()
        startObservingUI()
    }

    private fun startObservingUI() {
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                    override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                        viewModel.clicked(position)
                    }
                })
                .setOnItemLongClickListener(object : ItemClickSupport.OnItemLongClickListener {
                    override fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean {
                        viewModel.longClicked(position)
                        return true
                    }
                })
    }

    private fun startObservingViewModel() {
        viewModel.budgets.observe(this, Observer {
            adapter.budgetsRefreshed(budgets = it)
        })
    }

    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView.adapter = adapter
    }
}