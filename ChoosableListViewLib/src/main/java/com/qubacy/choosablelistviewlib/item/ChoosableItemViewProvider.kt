package com.qubacy.choosablelistviewlib.item

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import com.qubacy.choosablelistviewlib._common.util.resolveIntegerAttr
import com.qubacy.choosablelistviewlib.databinding.ComponentChoosableListItemBinding
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider
import com.qubacy.utility.baserecyclerview.item.data.BaseRecyclerViewItemData

class ChoosableItemViewProvider<
    ContentItemDataType : BaseRecyclerViewItemData,
    ContentViewProviderType : BaseRecyclerViewItemViewProvider<ContentItemDataType>
>(
    context: Context,
    attrs: AttributeSet?,
    contentViewProvider: ContentViewProviderType,
    divider: MaterialDivider? = null
) : ConstraintLayout(
    context, attrs
), BaseRecyclerViewItemViewProvider<ContentItemDataType> {
    companion object {
        const val TAG = "ChoosableItemView"

        const val DEFAULT_HINT_GUIDELINE_POSITION = 0.5f
        const val DEFAULT_SWIPE_BACK_ANIMATION_DURATION = 200L
    }

    @ColorInt
    private var mLeftSwipeBackgroundColor: Int = 0
    @ColorInt
    private var mRightSwipeBackgroundColor: Int = 0

    private var mLeftHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION
    private var mRightHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION

    private var mSwipeBackAnimationDuration: Long = DEFAULT_SWIPE_BACK_ANIMATION_DURATION

    private var mHeightInPx: Float? = null

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
        mSwipeBackAnimationDuration = context.theme.resolveIntegerAttr(
            R.attr.choosableListItemSwipeBackAnimationDuration).toLong()
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
            mHeightInPx?.toInt() ?: LayoutParams.WRAP_CONTENT
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

    override fun setViewProviderEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled

        contentViewProvider.setViewProviderEnabled(isEnabled)
    }

    fun swipeBack(
        onSwipeBackEnded: (() -> Unit)? = null
    ) {
        val contentView = contentViewProvider.getView()

        val endAction = {
            contentView.apply {
                translationX = 0f
            }

            onSwipeBackEnded?.invoke()
        }

        contentView.animate().apply {
            translationX(0f)

            duration = mSwipeBackAnimationDuration
        }.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) { endAction() }
            override fun onAnimationEnd(animation: Animator) { endAction() }
        }).start()
    }
}