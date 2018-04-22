package com.tammidev.day2daybudget.budget.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.extensions.inflate

class OtherFragment : Fragment() {

    companion object {
        private val SOME_KEY = "some_key";
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_other)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}