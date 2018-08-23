package co.en.archx.sample.ui.activity.main.list.itemview

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import co.en.archx.sample.R
import co.en.archx.sample.ui.activity.main.MainActivity
import co.en.archx.sample.ui.activity.main.list.RedditListItem
import javax.inject.Inject

class FooterItemView @Inject constructor(activity: MainActivity)
    : FrameLayout(activity), ItemView {

    init {
        val params = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams = params

        View.inflate(context, R.layout.post_list_footer_item, this)
    }

    override fun view(): View = this

    override fun setItem(item: RedditListItem) {

    }
}