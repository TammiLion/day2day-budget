package com.tammidev.day2daybudget.budget

import android.arch.lifecycle.Observer
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.tammidev.day2daybudget.app.AppDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class BudgetDatabaseTest {
    lateinit var mUserDao: BudgetDao
    lateinit var mDb: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mUserDao = mDb.budgetDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val budget = Budget("Test", 10.0, repeat = true)
        budget.id = 27
        mUserDao.insert(budget)
        val retrieved = mUserDao.get(27)

        val innerObserver: Observer<List<Budget>> = Observer {
            if (it!!.isNotEmpty()) {
                assertThat(it[0].id, equalTo(27))
            }
        }

        retrieved.observeForever(innerObserver)
    }
}