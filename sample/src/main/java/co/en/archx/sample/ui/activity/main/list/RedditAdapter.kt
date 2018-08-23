package co.en.archx.sample.ui.activity.main.list

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.en.archx.sample.ui.activity.main.MainScope
import co.en.archx.sample.ui.activity.main.list.itemview.FooterItemView
import co.en.archx.sample.ui.activity.main.list.itemview.RedditItemView
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Provider

@MainScope
class RedditAdapter @Inject constructor() : RecyclerView.Adapter<RedditViewHolder>() {

    companion object {

        const val REDDIT_ITEM_TYPE = 0

        const val FOOTER_ITEM_TYPE = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RedditListItem>() {

            override fun areItemsTheSame(oldItem: RedditListItem,
                                         newItem: RedditListItem): Boolean {

                return if(oldItem is RedditListItem.Reddit &&
                        newItem is RedditListItem.Reddit) {
                    oldItem.id == newItem.id
                }  else false
            }

            override fun areContentsTheSame(oldItem: RedditListItem,
                                            newItem: RedditListItem): Boolean {

                return if(oldItem is RedditListItem.Reddit &&
                        newItem is RedditListItem.Reddit) {
                    oldItem.id == newItem.id &&
                            oldItem.author == newItem.author &&
                            oldItem.subreddit == newItem.subreddit &&
                            oldItem.thumbnail == newItem.thumbnail &&
                            oldItem.ups == newItem.ups
                }  else false
            }
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    @Inject
    lateinit var redditItemViewProvider: Provider<RedditItemView>

    @Inject
    lateinit var footerItemViewProvider: Provider<FooterItemView>

    private val onClick = {
        index: Int ->

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditViewHolder {
        val view: View = when(viewType) {
            REDDIT_ITEM_TYPE -> redditItemViewProvider.get()
            FOOTER_ITEM_TYPE -> footerItemViewProvider.get()
            else -> throw IllegalArgumentException("Unkown item type")
        }

        return RedditViewHolder(view, onClick)
    }

    override fun getItemViewType(position: Int): Int {
        return when(differ.currentList[position]) {
            is RedditListItem.Reddit -> REDDIT_ITEM_TYPE
            is RedditListItem.Footer -> FOOTER_ITEM_TYPE
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RedditViewHolder, position: Int) {
        holder.setItem(differ.currentList[position])
    }

    fun setItems(posts: List<RedditListItem>) {
        differ.submitList(posts)
    }

}