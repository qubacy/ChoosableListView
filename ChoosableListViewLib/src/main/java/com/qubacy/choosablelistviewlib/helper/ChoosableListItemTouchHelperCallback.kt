package com.qubacy.choosablelistviewlib.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter
import com.qubacy.choosablelistviewlib.item.ChoosableItemView
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView
import kotlin.math.abs

class ChoosableListItemTouchHelperCallback(
    val swipeThreshold: Float = DEFAULT_SWIPE_THRESHOLD,
    val hintThreshold: Float = DEFAULT_HINT_THRESHOLD,
    private val mCallback: Callback
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    interface Callback {
        fun onItemSwiped(direction: SwipeDirection, position: Int)
    }

    companion object {
        const val TAG = "ChItemTouchHelperClback"

        const val DEFAULT_SWIPE_THRESHOLD = 0.6f
        const val DEFAULT_HINT_THRESHOLD = 0.5f
    }

    init {
        if (hintThreshold > swipeThreshold)
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
        return swipeThreshold
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
        val choosableViewHolder =
            (viewHolder as ChoosableListAdapter.ChoosableListItemViewHolder<*, *>)
        val itemView = choosableViewHolder.choosableItemView

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

        drawItemContent(c, itemView, itemView.contentView, dX)
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
        if (swipeProgress < hintThreshold) return itemHintView.resetAnimation()

        val mappedSwipeProgress = (swipeProgress - hintThreshold) *
                (1f / abs(hintThreshold - swipeThreshold))
        val preparedSwipeProcess = if (mappedSwipeProgress > 1f) 1f else mappedSwipeProgress

        itemHintView.animateWithProgress(preparedSwipeProcess)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        Log.d(TAG, "clearView() entering..")

        val itemViewHolder = (viewHolder as ChoosableListAdapter.ChoosableListItemViewHolder<*, *>)

        itemViewHolder.choosableItemView.resetView()
    }

    private fun drawItemContent(
        c: Canvas,
        itemView: View,
        itemContentView: View,
        dX: Float
    ) {
        itemContentView.translationX = dX

        val itemViewRect = createItemViewRect(itemView)
        val itemContentBitmap = createItemContentBitmap(itemViewRect)
        val itemContentCanvas = createItemContentCanvas(itemViewRect)

        itemContentView.draw(itemContentCanvas)
        c.drawBitmap(
            itemContentBitmap,
            itemViewRect.left.toFloat() + dX,
            itemViewRect.top.toFloat(),
            null
        )
    }

    open fun createItemViewRect(itemView: View): Rect {
        return Rect(itemView.left, itemView.top, itemView.right, itemView.bottom)
    }

    open fun createItemContentBitmap(itemViewRect: Rect): Bitmap {
        return Bitmap.createBitmap(
            itemViewRect.width(),
            itemViewRect.height(),
            Bitmap.Config.ARGB_8888
        )
    }

    open fun createItemContentCanvas(itemViewRect: Rect): Canvas {
        val itemContentBitmap =
            Bitmap.createBitmap(
                itemViewRect.width(),
                itemViewRect.height(),
                Bitmap.Config.ARGB_8888
            )

        return Canvas(itemContentBitmap)
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