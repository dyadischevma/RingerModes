package ru.dyadischevma.ringermodes

import android.Manifest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.test_server.AdbTerminal
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.dyadischevma.ringermodes.model.RingerModeRepository
import ru.dyadischevma.ringermodes.screens.CreatingScreen
import ru.dyadischevma.ringermodes.screens.MainScreen
import ru.dyadischevma.ringermodes.screens.RecyclerViewItem
import ru.dyadischevma.ringermodes.screens.systemsettings.AreYouSureScreen
import ru.dyadischevma.ringermodes.screens.systemsettings.DoNotDisturbGrantsScreen
import ru.dyadischevma.ringermodes.view.MainActivity


@RunWith(JUnitParamsRunner::class)
class CreateTest : TestCase() {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, false, true)

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private val mainScreen = MainScreen()
    private val createScreen = CreatingScreen()

    @Test
    @Parameters(value = ["Test, NORMAL", "Test2, VIBRATE"])
    fun test(testName: String, regime: String) = before {
        AdbTerminal.connect()
        try {
            flakySafely(timeoutMs = 200) {
                DoNotDisturbGrantsScreen().textViewAppName.click()
                AreYouSureScreen().buttonYes.click()
                adbServer.performShell("input keyevent 4")
            }
        } catch (e: Exception) {
            //do nothing
        }
    }.after {
        RingerModeRepository(activityTestRule.activity.application).deleteRingerMode(testName)
    }.run {
        step("1. Click new Regime") {
            mainScreen {
                createNewRegime()
            }
        }
        step("2. Enter name") {
            createScreen {
                enterName(testName)
                data
            }
        }
        step("3. Choose ringer mode") {
            createScreen.chooseRegime(regime)
        }
        step("4. Save mode") {
            createScreen.buttonSave.click()
        }
        step("5. Main screen contains mode") {
            mainScreen {
                recycler {
                    firstChild<RecyclerViewItem> {
                        name.hasText(testName)
                        mode.hasText(regime)
                    }
                }
            }
        }
    }
}