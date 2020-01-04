package com.example.yangchaoming.bappdemo.widget

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % this.spanCount
        if (this.includeEdge) {
            outRect.left = this.spacing - column * this.spacing / this.spanCount
            outRect.right = (column + 1) * this.spacing / this.spanCount
            if (position < this.spanCount) {
                outRect.top = this.spacing
            }

            outRect.bottom = this.spacing
        } else {
            outRect.left = column * this.spacing / this.spanCount
            outRect.right = this.spacing - (column + 1) * this.spacing / this.spanCount
            if (position >= this.spanCount) {
                outRect.top = this.spacing
            }
        }

    }
}
