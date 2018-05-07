package com.tammidev.day2daybudget.budget

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.budget.configure.ConfigureFragment
import com.tammidev.day2daybudget.budget.other.OtherFragment
import com.tammidev.day2daybudget.budget.overview.OverviewFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val overviewFragment = OverviewFragment()
    private val configureFragment = ConfigureFragment()
    private val otherFragment = OtherFragment()

    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        setupBottomNavigationMenu()
        setupView()
    }

    private fun setupView() {
        replaceFragment(overviewFragment)
    }

    private fun setupBottomNavigationMenu() {
        bottom_navigation_menu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_overview -> handleOverviewSelected()
                R.id.action_configure -> handleConfigureSelected()
                R.id.action_other -> handleOtherSelected()
                else -> {
                    Timber.e("Non-existent navigation item selected.")
                    false
                }
            }
        }
    }

    private fun handleOtherSelected(): Boolean {
        replaceFragment(otherFragment)
        return true
    }

    private fun handleOverviewSelected(): Boolean {
        replaceFragment(overviewFragment)
        return true
    }

    private fun handleConfigureSelected(): Boolean {
        replaceFragment(configureFragment)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit()
    }
}
