package com.mctech.library.view.ktx

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Handle the pagination when the list is almost in the and.
 */
class LoadNextPageScrollMonitor(
  private val loadNextPageHandler: () -> Unit,
) : RecyclerView.OnScrollListener() {

  private var lastItemVisiblePositionOnList = 0

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    val lastItemVisiblePosition = layoutManager.findLastVisibleItemPosition()

    // It is at last but one.
    if (isScrollingDown(lastItemVisiblePosition) && recyclerView.shouldLoadMoreItems()) {
      loadNextPageHandler.invoke()
    }

    lastItemVisiblePositionOnList = lastItemVisiblePosition
  }

  private fun isScrollingDown(lastItemVisiblePosition: Int): Boolean {
    return lastItemVisiblePosition > lastItemVisiblePositionOnList
  }

  private fun RecyclerView.shouldLoadMoreItems(): Boolean {
    val layoutManager = layoutManager as LinearLayoutManager

    val totalItemCount = layoutManager.itemCount
    val lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()

    return lastCompletelyVisibleItemPosition > totalItemCount - 3
  }
}