package com.tammidev.day2daybudget

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tammidev.day2daybudget.extensions.inflate

class ConfigureFragment : Fragment() {

    companion object {
        private val SOME_KEY = "some_key";
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_configure)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}