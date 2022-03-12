package com.codepath.apps.restclienttemplate

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.models.Tweet

private const val TAG = "EndlessRecycleView"
abstract class EndlessRecyclerViewScrollListener(private val mLayoutManager:LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var visibleThresholds = 5
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private var startingPageIndex = 0

    fun getLastVisibleItemId(tweets:List<Tweet>):Long {
        Log.d(TAG, "getLastVisibleItemId: ${tweets.last().id}")
        return tweets.last().id
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition()
        val totalItemCount = mLayoutManager.itemCount

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0){
                loading = true
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && (lastVisibleItemPosition + visibleThresholds)>totalItemCount){
            currentPage++
            onLoadMore(currentPage,totalItemCount,recyclerView)
            loading = true
        }
    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onLoadMore(page:Int,totalItemCount:Int,view: RecyclerView)
}