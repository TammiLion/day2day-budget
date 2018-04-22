package com.tammidev.day2daybudget.budget

import android.arch.lifecycle.LiveData
import com.tammidev.day2daybudget.app.AppDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BudgetRepo @Inject constructor(appDatabase: AppDatabase) {
    val budgetDao: BudgetDao = appDatabase.budgetDao()

    fun save(budget: Budget) {
        Single.fromCallable({ budgetDao.insert(budget) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun delete(vararg budget: Budget) {
        Single.fromCallable({ budgetDao.delete(*budget) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun update(budget: Budget) {
        Single.fromCallable({ budgetDao.update(budget) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun getAll(): LiveData<List<Budget>> {
        return budgetDao.getAll()
    }

    fun deleteBudgetsPast(date: DateTime) {
        Single.fromCallable({
            val budgets: List<Budget> = budgetDao.getBudgetsPastDate(date.millis)
            budgetDao.delete(*budgets.toTypedArray())
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

}