package com.tammidev.day2daybudget.app

import android.app.Application
import com.tammidev.day2daybudget.di.AppComponent
import com.tammidev.day2daybudget.di.AppModule
import com.tammidev.day2daybudget.di.DaggerAppComponent
import javax.inject.Inject

class D2dApp : Application() {

    private lateinit var component: AppComponent

    @Inject
    lateinit var appStartup: AppStartup

    override fun onCreate() {
        super.onCreate()
        setComponentAndInjectApp(DaggerAppComponent.builder().appModule(AppModule(this)).build())
        appStartup.startMeUp()
    }

    fun setComponentAndInjectApp(component: AppComponent) {
        this.component = component
        this.component.inject(this)
    }

    fun getComponent(): AppComponent {
        return component;
    }
}