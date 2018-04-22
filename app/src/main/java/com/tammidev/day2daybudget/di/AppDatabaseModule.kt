package com.tammidev.day2daybudget.di

import android.arch.persistence.room.Room
import com.tammidev.day2daybudget.app.AppDatabase
import com.tammidev.day2daybudget.app.Constants
import com.tammidev.day2daybudget.app.D2dApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: D2dApp): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, Constants.DATABASE_NAME).build();
    }
}