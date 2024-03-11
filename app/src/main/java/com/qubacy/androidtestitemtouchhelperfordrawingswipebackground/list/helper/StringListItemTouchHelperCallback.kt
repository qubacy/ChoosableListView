package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.list.helper

import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.list.adapter.StringListAdapter
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.list.item.hint.SwipeHintView
import kotlin.math.abs

class StringListItemTouchHelperCallback(
    private val mSwipeThreshold: Float = 0.5f,
    private val mCallback: Callback,
    @ColorInt
    private val mLeftSwipeBackgroundColor: Int = Color.RED,
    @ColorInt
    private val mRightSwipeBackgroundColor: Int = Color.GREEN
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    enum class SwipeDirection {
        LEFT, RIGHT;
    }

    interface Callback {
        fun onItemSwiped(direction: SwipeDirection, position: Int)
    }

    private var mSwipeHintAnimated: Boolean = false

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return mSwipeThreshold
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemBinding = (viewHolder as StringListAdapter.StringListItemViewHolder).itemBinding

        if (isHorizontalSwipe(dX, dY)) {
            val swipeDirection = getSwipeDirectionByDeltaX(dX)
            val swipeProgress = (abs(dX) / itemBinding.root.measuredWidth) // 0..1;

            val hintView = when (swipeDirection) {
                SwipeDirection.LEFT -> itemBinding.componentListItemBackgroundHintLeft
                SwipeDirection.RIGHT -> itemBinding.componentListItemBackgroundHintRight
            }

            if (!hintView.isInitialized()) hintView.init()

            adjustBackground(itemBinding.root, hintView, swipeDirection, swipeProgress)
        }

        drawItem(c, itemBinding.componentListItemContent, dX)
    }

    private fun adjustBackground(
        itemWrapperView: ConstraintLayout,
        itemHintView: SwipeHintView,
        swipeDirection: SwipeDirection,
        @FloatRange(0.0, 1.0) swipeProgress: Float
    ) {
        adjustBackgroundHint(itemHintView, swipeProgress)

        val backgroundColor = getSwipeBackgroundColorByDirection(swipeDirection)

        itemWrapperView.setBackgroundColor(backgroundColor)
    }

    private fun adjustBackgroundHint(
        itemHintView: SwipeHintView,
        @FloatRange(0.0, 1.0) swipeProgress: Float
    ) {
        if (swipeProgress < mSwipeThreshold) {
            itemHintView.resetAnimation()

            return
        }

        val mappedSwipeProgress = (swipeProgress - mSwipeThreshold) * (1f / abs(0.6f - mSwipeThreshold))
        val preparedSwipeProcess = if (mappedSwipeProgress > 1f) 1f else mappedSwipeProgress

        itemHintView.animateWithProgress(preparedSwipeProcess)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        val itemBinding = (viewHolder as StringListAdapter.StringListItemViewHolder).itemBinding

        itemBinding.componentListItemContent.translationX = 0f
    }

    private fun drawItem(
        c: Canvas,
        itemView: View,
        dX: Float
    ) {
        itemView.apply {
            translationX = dX
            draw(c)
        }
    }

    private fun isHorizontalSwipe(
        dX: Float,
        dY: Float
    ): Boolean {
        return (dY == 0f && dX != 0f)
    }

    @ColorInt
    private fun getSwipeBackgroundColorByDirection(swipeDirection: SwipeDirection): Int {
        return when (swipeDirection) {
            SwipeDirection.RIGHT -> mRightSwipeBackgroundColor
            SwipeDirection.LEFT -> mLeftSwipeBackgroundColor
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val swipeDirection = getSwipeDirectionByDirection(direction)

        return mCallback.onItemSwiped(swipeDirection, position)
    }

    private fun getSwipeDirectionByDirection(direction: Int): SwipeDirection {
        return when (direction) {
            ItemTouchHelper.LEFT -> SwipeDirection.LEFT
            ItemTouchHelper.RIGHT -> SwipeDirection.RIGHT
            else -> throw IllegalArgumentException()
        }
    }

    private fun getSwipeDirectionByDeltaX(dX: Float): SwipeDirection {
        return if (dX < 0) SwipeDirection.LEFT
        else SwipeDirection.RIGHT
    }
}