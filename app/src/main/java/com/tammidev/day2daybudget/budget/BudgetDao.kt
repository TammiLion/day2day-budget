package com.tammidev.day2daybudget.budget

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface BudgetDao {

    @Query("SELECT * from budget WHERE id=:id")
    fun get(id: Int): LiveData<List<Budget>>

    @Query("SELECT * from budget")
    fun getAll(): LiveData<List<Budget>>

    @Insert(onConflict = REPLACE)
    fun insert(budget: Budget)

    @Update
    fun update(budget: Budget)

    @Delete
    fun delete(vararg budget: Budget)

    @Query("SELECT * FROM budget WHERE end_date > :date")
    fun getBudgetsPastDate(date: Long): List<Budget>

}