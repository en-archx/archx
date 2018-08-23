package co.en.archx.sample.ui.activity.main.list.itemview

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import co.en.archx.sample.R
import co.en.archx.sample.ui.activity.main.MainActivity
import co.en.archx.sample.ui.activity.main.list.RedditListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_list_reddit_item.view.*
import javax.inject.Inject

class RedditItemView @Inject constructor(activity: MainActivity)
    : FrameLayout(activity), ItemView {

    @Inject
    lateinit var picasso: Picasso

    init {
        val params = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams = params

        View.inflate(context, R.layout.post_list_reddit_item, this)
    }

    override fun view(): View = this

    override fun setItem(item: RedditListItem) {
        item as RedditListItem.Reddit

        title.text = item.title
        subreddit.text = "r/${item.subreddit}"
        author.text = "Posted by ${item.author}"
        picasso.load(item.thumbnail).into(thumbnail)
        ups.text = "${item.ups}"
    }
}