package ru.dyadischevma.ringermodes.screens

import com.agoda.kakao.text.KButton
import com.kaspersky.kaspresso.screens.KScreen
import ru.dyadischevma.ringermodes.R

public class MainScreen : KScreen<MainScreen>() {
    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = this.javaClass

    val buttonCreate = KButton { withId(R.id.floatingActionButton) }

    fun createNewRegime() {
        buttonCreate {
            click()
        }
    }
}