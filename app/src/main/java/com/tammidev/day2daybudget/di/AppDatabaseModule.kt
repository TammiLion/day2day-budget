package com.tammidev.day2daybudget.di

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import com.tammidev.day2daybudget.app.AppDatabase
import com.tammidev.day2daybudget.app.Constants
import com.tammidev.day2daybudget.app.D2dApp
import dagger.Module
import dagger.Provides
import org.joda.time.DateTime
import javax.inject.Singleton


@Module
open class AppDatabaseModule {

    @Provides
    @Singleton
    open fun provideAppDatabase(app: D2dApp): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, Constants.DATABASE_NAME)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE budget " + " ADD COLUMN expenses TEXT NOT NULL DEFAULT []")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE budget " + " ADD COLUMN start_date INTEGER NOT NULL DEFAULT " + DateTime.now().millis)
    }
}
