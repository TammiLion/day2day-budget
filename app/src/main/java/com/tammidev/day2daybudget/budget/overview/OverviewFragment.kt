package com.tammidev.day2daybudget.budget.overview

import ItemClickSupport
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.BudgetViewModelFactory
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

private const val ADD_EXPENSE_DIALOG_TAG = "add_expense_dialog"

class OverviewFragment : Fragment() {

    private val editEvents = PublishSubject.create<Int>()
    private val deleteEvents = PublishSubject.create<Int>()
    private val adapter: BudgetAdapter = BudgetAdapter(listOf(), editEvents, deleteEvents)
    private val disposables: MutableList<Disposable> = mutableListOf()
    private var dialog: AlertDialog? = null

    companion object {
        private val SOME_KEY = "some_key"
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

    override fun onDestroyView() {
        disposables.forEach {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        dialog?.dismiss()
        super.onDestroyView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        startObservingViewModel()
        startObservingUI()
        startObservingAdapterEvents()
    }

    private fun startObservingAdapterEvents() {
        disposables.add(editEvents.subscribe { viewModel.requestToEdit(it) })
        disposables.add(deleteEvents.subscribe { showDeleteConfirmationDialog(it) })
    }

    private fun startObservingUI() {
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                    override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                        val budgetId = viewModel.getBudgetId(position)
                        if (budgetId != null) {
                            showExpenseDialog(budgetId)
                        }
                    }
                })
                .setOnItemLongClickListener(object : ItemClickSupport.OnItemLongClickListener {
                    override fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean {
                        viewModel.longClicked(position)
                        return true
                    }
                })
    }

    private fun showExpenseDialog(budgetId: Int) {
        val ft: FragmentTransaction? = fragmentManager?.beginTransaction()
        val prev: Fragment? = fragmentManager?.findFragmentByTag(ADD_EXPENSE_DIALOG_TAG)
        ft?.apply {
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            AddExpenseDialog.newInstance(budgetId).show(ft, ADD_EXPENSE_DIALOG_TAG)
        }
    }

    private fun showDeleteConfirmationDialog(id: Int) {
        dialog = AlertDialog.Builder(context)
                .setTitle("Delete budget")
                .setMessage("Are you sure you want to delete this budget?")
                .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    viewModel.requestToDelete(id)
                    dialog.dismiss()
                }
                .show()
    }

    private fun startObservingViewModel() {
        viewModel.budgets.observe(this, Observer {
            adapter.budgetsRefreshed(budgets = it)
        })
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}