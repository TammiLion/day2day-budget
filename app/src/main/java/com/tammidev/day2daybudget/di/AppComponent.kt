package com.tammidev.day2daybudget.di

import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.configure.ConfigureFragment
import com.tammidev.day2daybudget.budget.overview.OverviewFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by troep on 9/24/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, AppStartupModule::class, AppDatabaseModule::class))
interface AppComponent {
    fun inject(app: D2dApp)
    fun inject(app: OverviewFragment)
    fun inject(app: ConfigureFragment)
}