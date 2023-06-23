package com.unc0ded.listedapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val spaceSize: Int, private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                when (orientation) {
                    RecyclerView.HORIZONTAL -> left = spaceSize
                    RecyclerView.VERTICAL -> top = spaceSize
                }
            }
        }
    }
}