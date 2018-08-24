package co.en.archx.sample.ext

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log

enum class ScrollState {
    SCROLLING, HIT_TOP, HIT_BOTTOM, UNKNOWN
}

fun RecyclerView.state(threshold: Int = 0): ScrollState {
    var lastVisibleItemPosition = 0
    val lastItemPosition = layoutManager.itemCount - 1
    val _layoutmanager = layoutManager
    var orientation = 1

    when (_layoutmanager) {
        is GridLayoutManager -> {
            orientation = _layoutmanager.orientation
            lastVisibleItemPosition = _layoutmanager.findLastVisibleItemPosition()
        }

        is LinearLayoutManager -> {
            orientation = _layoutmanager.orientation
            lastVisibleItemPosition = _layoutmanager.findLastVisibleItemPosition()
        }

        is StaggeredGridLayoutManager -> {
            orientation = _layoutmanager.orientation
            lastVisibleItemPosition = _layoutmanager.findLastVisibleItemPositions(null)
                    .lastOrNull() ?: 0
        }
    }

    val offset = if(orientation == 1)
        computeVerticalScrollOffset()
    else
        computeHorizontalScrollOffset()

    return if(lastItemPosition > 0)
        when {
            offset == 0 -> ScrollState.HIT_TOP
            (lastVisibleItemPosition + threshold) >= lastItemPosition -> ScrollState.HIT_BOTTOM
            else -> ScrollState.SCROLLING
        }
    else ScrollState.UNKNOWN
}