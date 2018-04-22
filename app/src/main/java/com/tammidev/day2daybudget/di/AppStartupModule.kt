package com.tammidev.day2daybudget.di

import com.tammidev.day2daybudget.app.AppStartup
import com.tammidev.day2daybudget.app.D2dApp
import dagger.Module
import dagger.Provides

@Module
class AppStartupModule {

    @Provides
    fun provideAppStartup(app: D2dApp): AppStartup = AppStartup(app)
}