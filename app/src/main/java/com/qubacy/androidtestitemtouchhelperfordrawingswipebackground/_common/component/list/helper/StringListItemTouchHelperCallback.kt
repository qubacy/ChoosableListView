package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.helper

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list._common.SwipeDirection
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.adapter.ChoosableListAdapter
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.ChoosableItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.hint.SwipeHintView
import kotlin.math.abs

class StringListItemTouchHelperCallback(
    private val mSwipeThreshold: Float = DEFAULT_SWIPE_THRESHOLD,
    private val mHintThreshold: Float = DEFAULT_HINT_THRESHOLD,
    private val mCallback: Callback
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    interface Callback {
        fun onItemSwiped(direction: SwipeDirection, position: Int)
    }

    companion object {
        const val TAG = "SLItemTouchHelperClback"

        const val DEFAULT_SWIPE_THRESHOLD = 0.5f
        const val DEFAULT_HINT_THRESHOLD = 0.6f
    }

    init {
        if (mHintThreshold <= mSwipeThreshold)
            throw IllegalArgumentException()
    }

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
        val itemView = viewHolder.itemView as ChoosableItemView<*, *>

        if (isHorizontalSwipe(dX, dY)) {
            val swipeDirection = getSwipeDirectionByDeltaX(dX)
            val swipeProgress = (abs(dX) / itemView.measuredWidth) // 0..1;

            val hintView = when (swipeDirection) {
                SwipeDirection.LEFT -> itemView.getLeftSwipingHintView()
                SwipeDirection.RIGHT -> itemView.getRightSwipingHintView()
            }

            if (!hintView.isInitialized()) hintView.init()

            adjustBackground(itemView, hintView, swipeDirection, swipeProgress)
        }

        drawItem(c, itemView.contentView, dX)
    }

    private fun adjustBackground(
        itemView: ChoosableItemView<*, *>,
        itemHintView: SwipeHintView,
        swipeDirection: SwipeDirection,
        @FloatRange(0.0, 1.0) swipeProgress: Float
    ) {
        adjustBackgroundHint(itemHintView, swipeProgress)
        itemView.prepareForSwipeDirection(swipeDirection)
    }

    private fun adjustBackgroundHint(
        itemHintView: SwipeHintView,
        @FloatRange(0.0, 1.0) swipeProgress: Float
    ) {
        if (swipeProgress < mSwipeThreshold) return itemHintView.resetAnimation()

        val mappedSwipeProgress = (swipeProgress - mSwipeThreshold) *
                (1f / abs(mHintThreshold - mSwipeThreshold))
        val preparedSwipeProcess = if (mappedSwipeProgress > 1f) 1f else mappedSwipeProgress

        itemHintView.animateWithProgress(preparedSwipeProcess)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        Log.d(TAG, "clearView() entering..")

        val itemViewHolder = (viewHolder as ChoosableListAdapter.ChoosableListItemViewHolder<*, *>)

        itemViewHolder.choosableItemView.resetView()
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