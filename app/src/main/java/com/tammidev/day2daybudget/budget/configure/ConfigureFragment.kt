package com.tammidev.day2daybudget.budget.configure

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.BudgetViewModelFactory
import com.tammidev.day2daybudget.extensions.closeKeyboard
import com.tammidev.day2daybudget.extensions.inflate
import kotlinx.android.synthetic.main.fragment_configure.*
import javax.inject.Inject

class ConfigureFragment : Fragment() {

    companion object {
        private val SOME_KEY = "some_key"
    }

    @Inject
    lateinit var viewModelFactory: BudgetViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ConfigureViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_configure)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context?.applicationContext as D2dApp).getComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingViewModel()
        startObservingUI()
    }

    private fun startObservingUI() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.nameChanged(s.toString())
            }
        })

        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.amountChanged(s.toString())
            }
        })

        amountEditText.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                closeKeyboard()
                true
            } else {
                false
            }
        })

        repeatSwitch.setOnCheckedChangeListener { _, isChecked -> viewModel.repeatSwitchToggled(isChecked) }

        daysPicker.setValueChangedListener({ _, _ -> viewModel.daysPicked(daysPicker.value) })

        saveBtn.setOnClickListener({
            viewModel.save()
            resetUI()
        })
    }

    private fun resetUI() {
        closeKeyboard()
        Toast.makeText(context, "Your new budget " + nameEditText.text.toString() + " is saved!", Toast.LENGTH_SHORT).show()
        nameEditText.setText("")
        if (repeatSwitch.isChecked) repeatSwitch.toggle()
    }

    private fun startObservingViewModel() {
    }
}