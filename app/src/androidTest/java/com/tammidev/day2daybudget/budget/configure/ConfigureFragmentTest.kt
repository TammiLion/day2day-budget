package com.tammidev.day2daybudget.budget.configure

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.tammidev.day2daybudget.R
import com.tammidev.day2daybudget.app.AppDatabase
import com.tammidev.day2daybudget.app.D2dApp
import com.tammidev.day2daybudget.budget.MainActivity
import com.tammidev.day2daybudget.di.AppComponent
import com.tammidev.day2daybudget.di.AppModule
import com.tammidev.day2daybudget.di.AppStartupModule
import dagger.Component
import dagger.Module
import dagger.Provides
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
class ConfigureFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun before() {
        val context = InstrumentationRegistry.getTargetContext()
        val app = context.applicationContext as D2dApp
        val component = DaggerTestComponent.builder().appModule(AppModule(app)).build() //if this isn't working, rebuild and run compileDebugAndroidTestKotlin
        app.setComponentAndInjectApp(component)
        activityRule.launchActivity(null)
        onView(withId(R.id.action_configure)).perform(click())
    }

    @Test
    fun test() {
        onView(withId(R.id.amountEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.nameEditText)).check(matches(isDisplayed()))
        onView(withId(R.id.amountEditText)).perform(typeText("20.00"))
        onView(withId(R.id.nameEditText)).perform(typeText("blah"))

        closeSoftKeyboard()
        onView(withId(R.id.saveBtn)).perform(click())

        onView(withId(R.id.action_overview)).perform(click())
        onView(withText("blah")).check(matches(isDisplayed()))
    }
}

@Singleton
@Component(modules = arrayOf(AppModule::class, AppStartupModule::class, TestDatabaseModule::class))
interface TestComponent : AppComponent

@Module
open class TestDatabaseModule {

    @Provides
    @Singleton
    open fun provideAppDatabase(app: D2dApp): AppDatabase {
        return Room.inMemoryDatabaseBuilder(app, AppDatabase::class.java)
                .build()
    }
}