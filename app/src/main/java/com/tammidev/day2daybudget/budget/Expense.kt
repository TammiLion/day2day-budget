package com.tammidev.day2daybudget.budget

import org.joda.time.DateTime

/**
 * Created by troep on 5/01/18.
 */

data class Expense(var name: String = "New Budget",
                   var cost: Double = 0.0,
                   var date: Long = DateTime.now().millis,
                   var tags: List<String> = ArrayList())

