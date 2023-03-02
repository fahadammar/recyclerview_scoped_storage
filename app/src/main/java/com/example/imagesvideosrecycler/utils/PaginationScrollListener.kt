package com.example.imagesvideosrecycler.utils

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {

    private val visibleThreshold = 5
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private val TAG = "paginationScroll"

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        Log.d(TAG, "onScrolled: total item count --> $totalItemCount")
        Log.d(TAG, "onScrolled: last Visible Item Position --> $lastVisibleItemPosition")

        if (totalItemCount < previousTotalItemCount) {
            resetState()
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            Log.d(TAG, "onScrolled: before load more --> totalItemCount: $totalItemCount")
            onLoadMore(totalItemCount)
            loading = true
        }
    }

    private fun resetState() {
        this.previousTotalItemCount = startingPageIndex
        this.loading = true
    }

    abstract fun onLoadMore(totalItemCount: Int)
}
