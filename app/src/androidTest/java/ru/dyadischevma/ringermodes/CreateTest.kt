package ru.dyadischevma.ringermodes

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.dyadischevma.ringermodes.screens.MainScreen
import ru.dyadischevma.ringermodes.view.MainActivity


@RunWith(AndroidJUnit4::class)
class CreateTest : TestCase() {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, false, true)

    private val mainScreen = MainScreen()

    @Test
    fun test() = before {
        // No-op
    }.after {
        // No-op
    }.run {
        step("1. Click new Regime") {
            mainScreen {
                createNewRegime()
            }
        }
    }
}