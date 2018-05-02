package com.tammidev.day2daybudget.budget.overview

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
import kotlinx.android.synthetic.main.dialog_add_expense.*

class AddExpenseDialog : DialogFragment() {

    private var amount: Double = 0.00
    private val tags: MutableList<String> = ArrayList()
    private val adapter = TagAdapter(tags)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(4, GridLayout.VERTICAL)
        tagsRecyclerView.layoutManager = layoutManager
        tagsRecyclerView.adapter = adapter

        tagsEditText.setOnEditorActionListener({ _, actionId, _ ->
            Log.d("Debug", "le action" + actionId + " " + EditorInfo.IME_ACTION_UNSPECIFIED)
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
        })

        saveBtn.setOnClickListener {
            //TODO is amount filled in?
            if (amount > 0.00) {

            } else {
                Toast.makeText(context, "lalala", Toast.LENGTH_SHORT).show()
            }
        }

    }
}