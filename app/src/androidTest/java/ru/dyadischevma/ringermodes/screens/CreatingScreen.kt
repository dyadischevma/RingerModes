package ru.dyadischevma.ringermodes.screens

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.text.KButton
import com.kaspersky.kaspresso.screens.KScreen
import ru.dyadischevma.ringermodes.R
import ru.dyadischevma.ringermodes.data.RingerMode
import ru.dyadischevma.ringermodes.view.RegimeActivity

class CreatingScreen : KScreen<CreatingScreen>() {
    override val layoutId: Int = R.layout.activity_regime
    override val viewClass: Class<*> = RegimeActivity::class.java

    private val editTextName = KEditText { withId(R.id.editTextTextName) }

    private val radioButtonNormal = KButton { withId(R.id.radioButtonNormal) }
    private val radioButtonSilent = KButton { withId(R.id.radioButtonSilent) }
    private val radioButtonVibrate = KButton { withId(R.id.radioButtonVibrate) }

    val buttonSave = KButton { withId(R.id.floatingActionButtonSave) }

    fun enterName(name: String) {
        editTextName.typeText(name)
    }

    fun chooseRegime(regime: String) {
        when (RingerMode.valueOf(regime)) {
            RingerMode.NORMAL -> {
                radioButtonNormal.click()
            }
            RingerMode.VIBRATE -> {
                radioButtonVibrate.click()
            }
            else -> {
                radioButtonSilent.click()
            }
        }
    }
}