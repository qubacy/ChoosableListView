package com.qubacy.choosablelistview._common.component.list.item

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.divider.MaterialDivider
import com.qubacy.choosablelistview.R
import com.qubacy.choosablelistview._common.component.list._common.SwipeDirection
import com.qubacy.choosablelistview.databinding.ComponentChoosableListItemBinding
import com.qubacy.choosablelistview._common.component.list.item.content.ChoosableItemContentView
import com.qubacy.choosablelistview._common.component.list.item.content.data.ChoosableItemContentViewData
import com.qubacy.choosablelistview._common.component.list.item.hint.SwipeHintView

class ChoosableItemView<ContentViewType, ContentItemDataType : ChoosableItemContentViewData>(
    context: Context,
    attrs: AttributeSet?,
    contentView: ContentViewType,
    divider: MaterialDivider? = null
) : ConstraintLayout(
    context, attrs
) where ContentViewType : View, ContentViewType : ChoosableItemContentView<ContentItemDataType> {
    companion object {
        const val TAG = "ChoosableItemView"

        const val DEFAULT_HINT_GUIDELINE_POSITION = 0.5f
    }

    @ColorInt
    private var mLeftSwipeBackgroundColor: Int = 0
    @ColorInt
    private var mRightSwipeBackgroundColor: Int = 0

    private var mLeftHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION
    private var mRightHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION

    private var mHeightInPx: Float = 0f

    private lateinit var mBinding: ComponentChoosableListItemBinding

    val contentView: ContentViewType = contentView

    init {
        initValues()
        inflate(contentView, divider)
        initLayoutParams(contentView)
    }

    private fun initValues() {
        val resources = context.resources
        val leftHintGuidelinePosition =
            ResourcesCompat.getFloat(resources,
                R.dimen.component_choosable_list_item_left_hint_guideline_position
            )

        mLeftHintGuidelinePosition = leftHintGuidelinePosition
        mRightHintGuidelinePosition = 1 - leftHintGuidelinePosition

        mHeightInPx = resources.getDimension(R.dimen.component_choosable_list_item_height)
    }

    private fun inflate(contentView: View, divider: MaterialDivider? = null) {
        val layoutInflater = LayoutInflater.from(context)

        mBinding = ComponentChoosableListItemBinding.inflate(layoutInflater, this)

        addView(contentView)
        addView(divider)
    }

    private fun initLayoutParams(contentView: View, divider: MaterialDivider? = null) {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            mHeightInPx.toInt()
        )

        mBinding.componentChoosableListItemGuidelineHorizontalHintLeft
            .setGuidelinePercent(mLeftHintGuidelinePosition)
        mBinding.componentChoosableListItemGuidelineHorizontalHintRight
            .setGuidelinePercent(mRightHintGuidelinePosition)

        contentView.apply {
            this@apply.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
        divider?.apply {
            this@apply.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }
    }

    fun resetView() {
        contentView.translationX = 0f

        mBinding.componentChoosableListItemHintLeft.apply {
            if (isInitialized()) resetAnimation()
        }
        mBinding.componentChoosableListItemHintRight.apply {
            if (isInitialized()) resetAnimation()
        }
    }

    fun getLeftSwipingHintView(): SwipeHintView {
        return mBinding.componentChoosableListItemHintLeft
    }

    fun getRightSwipingHintView(): SwipeHintView {
        return mBinding.componentChoosableListItemHintRight
    }

    fun prepareForSwipeDirection(swipeDirection: SwipeDirection) {
        when (swipeDirection) {
            SwipeDirection.LEFT -> prepareForLeftSwipe()
            SwipeDirection.RIGHT -> prepareForRightSwipe()
        }
    }

    fun setLeftSwipeBackgroundColor(@ColorInt color: Int) {
        mLeftSwipeBackgroundColor = color
    }

    fun setRightSwipeBackgroundColor(@ColorInt color: Int) {
        mRightSwipeBackgroundColor = color
    }

    private fun prepareForLeftSwipe() {
        setBackgroundColor(mLeftSwipeBackgroundColor)
    }

    private fun prepareForRightSwipe() {
        setBackgroundColor(mRightSwipeBackgroundColor)
    }
}