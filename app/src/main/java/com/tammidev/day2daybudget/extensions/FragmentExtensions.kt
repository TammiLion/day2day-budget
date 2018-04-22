package com.tammidev.day2daybudget.extensions

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager


/**
 * Created by troep on 8/27/17.
 */

fun Fragment.closeKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}