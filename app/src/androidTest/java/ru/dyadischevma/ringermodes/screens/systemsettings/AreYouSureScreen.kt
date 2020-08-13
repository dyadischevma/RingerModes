package ru.dyadischevma.ringermodes.screens.systemsettings

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.screen.UiScreen

class AreYouSureScreen: UiScreen<AreYouSureScreen>() {
    override val packageName: String = "com.android.settings"

    val buttonYes = UiButton{withText("ALLOW")}
}