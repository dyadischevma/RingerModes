package ru.dyadischevma.ringermodes.screens.systemsettings

import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.components.kautomator.screen.UiScreen

class DoNotDisturbGrantsScreen: UiScreen<DoNotDisturbGrantsScreen>() {
    override val packageName: String = "com.android.settings"

    val textViewAppName = UiTextView {withText("Ringer Modes")}
}