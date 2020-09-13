package com.goddoro.common.common.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * created By DORO 2020/08/16
 */

class GridSpacingItemDecoration(
    spanCount: Int,
    spacing_left: Int,
    spacing_top: Int,
    spacing_first_line_top: Int
) :
    RecyclerView.ItemDecoration() {
    private val spanCount: Int
    private val spacing: Int
    private val spacing_top: Int
    private val spacing_first_line_top: Int
    private val includeEdge: Boolean
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item phases_position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left =
                spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing_top
            }
            outRect.bottom = spacing_top // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing_top // item top
            } else {
                // 맨 윗줄의 top spacing
                outRect.top = spacing_first_line_top
            }
        }
    }

    init {
        this.spanCount = spanCount
        spacing = spacing_left
        includeEdge = false
        this.spacing_top = spacing_top
        this.spacing_first_line_top = spacing_first_line_top
    }
}