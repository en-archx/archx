package co.en.archx.sample.ui.activity.main.list.itemview

import android.view.View
import co.en.archx.sample.ui.activity.main.list.RedditListItem

interface ItemView {

    fun view(): View

    fun setItem(item: RedditListItem)

}