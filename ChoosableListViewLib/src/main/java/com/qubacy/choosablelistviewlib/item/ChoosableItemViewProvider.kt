package com.qubacy.choosablelistviewlib.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.divider.MaterialDivider
import com.qubacy.choosablelistviewlib.R
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistviewlib._common.util.resolveDimenAttr
import com.qubacy.choosablelistviewlib._common.util.resolveFractionAttr
import com.qubacy.choosablelistviewlib.databinding.ComponentChoosableListItemBinding
import com.qubacy.choosablelistviewlib.item.content.ChoosableItemContentViewProvider
import com.qubacy.choosablelistviewlib.item.content.data.ChoosableItemContentViewData
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView

class ChoosableItemViewProvider<
    ContentItemDataType : ChoosableItemContentViewData,
    ContentViewProviderType : ChoosableItemContentViewProvider<ContentItemDataType>
>(
    context: Context,
    attrs: AttributeSet?,
    contentViewProvider: ContentViewProviderType,
    divider: MaterialDivider? = null
) : ConstraintLayout(
    context, attrs
), ChoosableItemContentViewProvider<ContentItemDataType> {
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

    val contentViewProvider: ContentViewProviderType = contentViewProvider

    init {
        initValues()
        inflate(contentViewProvider.getView(), divider)
        initLayoutParams(contentViewProvider.getView())
    }

    private fun initValues() {
        val leftHintGuidelinePosition = context.theme
            .resolveFractionAttr(R.attr.choosableListItemLeftHintGuidelinePosition)

        mLeftHintGuidelinePosition = leftHintGuidelinePosition
        mRightHintGuidelinePosition = 1 - leftHintGuidelinePosition

        mHeightInPx = context.theme.resolveDimenAttr(R.attr.choosableListItemHeight)
    }

    private fun inflate(contentView: View, divider: MaterialDivider? = null) {
        val layoutInflater = LayoutInflater.from(context)

        mBinding = ComponentChoosableListItemBinding.inflate(layoutInflater, this)

        addView(contentView)

        if (divider != null) addView(divider)
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
        scaleY = 1f
        translationY = 0f

        contentViewProvider.getView().translationX = 0f

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

    override fun setData(contentItemData: ContentItemDataType) {
        contentViewProvider.setData(contentItemData)
    }

    override fun getView(): View {
        return this
    }
}