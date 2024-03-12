package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.R
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.databinding.ComponentListItemBinding
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.ContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.data.ContentItemData
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.hint.SwipeHintView

class ChoosableItemView<ContentViewType, ContentItemDataType : ContentItemData>(
    context: Context,
    attrs: AttributeSet?,
    contentView: ContentViewType
) : ConstraintLayout(
    context, attrs
) where ContentViewType : View, ContentViewType : ContentItemView<ContentItemDataType> {
    companion object {
        const val DEFAULT_HINT_GUIDELINE_POSITION = 0.5f
    }

    private var mLeftHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION
    private var mRightHintGuidelinePosition: Float = DEFAULT_HINT_GUIDELINE_POSITION

    private var mHeightInPx: Float = 0f

    private lateinit var mBinding: ComponentListItemBinding

    val contentView: ContentViewType = contentView

    init {
        initValues()
        inflate(contentView)
        initLayoutParams(contentView)
    }

    private fun initValues() {
        val resources = context.resources
        val leftHintGuidelinePosition =
            ResourcesCompat.getFloat(resources,
                R.dimen.component_list_item_left_hint_guideline_position
            )

        mLeftHintGuidelinePosition = leftHintGuidelinePosition
        mRightHintGuidelinePosition = 1 - leftHintGuidelinePosition

        mHeightInPx = resources.getDimension(R.dimen.component_list_item_height)
    }

    private fun inflate(contentView: View) {
        val layoutInflater = LayoutInflater.from(context)

        mBinding = ComponentListItemBinding.inflate(
            layoutInflater, this
        ).apply {
            addView(contentView)
        }
    }

    private fun initLayoutParams(contentView: View) {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            mHeightInPx.toInt()
        )

        mBinding.componentListItemGuidelineHorizontalHintLeft
            .setGuidelinePercent(mLeftHintGuidelinePosition)
        mBinding.componentListItemGuidelineHorizontalHintRight
            .setGuidelinePercent(mRightHintGuidelinePosition)

        contentView.apply {
            this@apply.layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    fun resetView() {
        contentView.translationX = 0f

        mBinding.componentListItemBackgroundHintLeft.apply {
            if (isInitialized()) resetAnimation()
        }
        mBinding.componentListItemBackgroundHintRight.apply {
            if (isInitialized()) resetAnimation()
        }
    }

    fun getLeftSwipingHintView(): SwipeHintView {
        return mBinding.componentListItemBackgroundHintLeft
    }

    fun getRightSwipingHintView(): SwipeHintView {
        return mBinding.componentListItemBackgroundHintRight
    }
}