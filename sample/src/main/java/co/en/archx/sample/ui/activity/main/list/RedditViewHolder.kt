package co.en.archx.sample.ui.activity.main.list

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import co.en.archx.sample.ui.activity.main.list.itemview.ItemView

class RedditViewHolder(val view: View,
                       val onClick: (Int) -> Unit)
    : RecyclerView.ViewHolder(view) {

    init {
        (view as ItemView).view().setOnClickListener {
            onClick(adapterPosition)
        }
    }

    fun setItem(item: RedditListItem) {
        view as ItemView
        view.setItem(item)
    }

}