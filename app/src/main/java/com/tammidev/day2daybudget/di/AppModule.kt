package com.tammidev.day2daybudget.di

import com.tammidev.day2daybudget.app.D2dApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by troep on 9/24/17.
 */
@Module
open class AppModule(val app: D2dApp) {

    @Provides
    @Singleton
    open fun provideApp(): D2dApp {
        return app
    }
}