package ru.dyadischevma.ringermodes.screens

import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KButton
import com.kaspersky.kaspresso.screens.KScreen
import ru.dyadischevma.ringermodes.R
import ru.dyadischevma.ringermodes.view.MainActivity

class MainScreen : KScreen<MainScreen>() {
    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val buttonCreate = KButton { withId(R.id.floatingActionButton) }
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.recyclerView)
    }, itemTypeBuilder = {
        itemType(::RecyclerViewItem)
    })

    fun createNewRegime() {
        buttonCreate {
            click()
        }
    }


}