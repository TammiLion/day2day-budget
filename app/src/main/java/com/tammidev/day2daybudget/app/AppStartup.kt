package com.tammidev.day2daybudget.app

import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.tammidev.day2daybudget.BuildConfig
import net.danlew.android.joda.JodaTimeAndroid
import timber.log.Timber
import javax.inject.Inject

class AppStartup @Inject constructor(val app: D2dApp) {
    init {
        startMeUp()
    }

    fun startMeUp() {
        JodaTimeAndroid.init(app)
        setupTimber()
        setupStetho()
        setupStrictMode()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupStrictMode() {
        if (BuildConfig.DEBUG) StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                //.penaltyDeath()
                .build())
    }

    private fun setupStetho() {
        Stetho.initializeWithDefaults(app)
    }
}