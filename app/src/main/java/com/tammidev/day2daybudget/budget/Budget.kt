package com.tammidev.day2daybudget.budget

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.joda.time.DateTime

/**
 * Created by troep on 8/27/17.
 */

const val DEFAULT_BUDGET_DURATION: Int = 30

@Entity
data class Budget(var name: String = "New Budget",
                  @ColumnInfo(name = "total_amount") var totalAmount: Double = 0.0,
                  @ColumnInfo(name = "start_date") var startDate: Long = DateTime.now().millis,
                  @ColumnInfo(name = "end_date") var endDate: Long = DateTime.now().plusDays(DEFAULT_BUDGET_DURATION).millis,
                  var repeat: Boolean = false,
                  @ColumnInfo(name = "amount_spent") var amountSpent: Double = 0.0,
                  var expenses: MutableList<Expense> = mutableListOf()) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

