package com.tammidev.day2daybudget.app

import com.tammidev.day2daybudget.BuildConfig
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject

class AppStartup @Inject constructor(val app: D2dApp) {
    init {
        startMeUp()
    }

    fun startMeUp() {
        JodaTimeAndroid.init(app);
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}