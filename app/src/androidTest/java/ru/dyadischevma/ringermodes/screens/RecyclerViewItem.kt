package ru.dyadischevma.ringermodes.screens

import android.content.ClipData
import android.view.View
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.dyadischevma.ringermodes.R


class RecyclerViewItem(parent: Matcher<View>) : KRecyclerItem<ClipData.Item>(parent) {
    val name: KTextView = KTextView(parent) { withId(R.id.textViewName) }
    val mode: KTextView = KTextView(parent) { withId(R.id.textViewRingerMode) }
}