package com.qubacy.choosablelistviewlib.adapter.producer

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.google.android.material.divider.MaterialDivider
import com.qubacy.choosablelistviewlib.R
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView
import com.qubacy.choosablelistviewlib._common.util.resolveColorAttr
import com.qubacy.choosablelistviewlib._common.util.resolveDimenAttr
import com.qubacy.choosablelistviewlib._common.util.resolveDrawableAttr
import com.qubacy.choosablelistviewlib._common.util.resolveStringAttr
import com.qubacy.utility.baserecyclerview.adapter.producer.BaseRecyclerViewItemViewProviderProducer
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider
import com.qubacy.utility.baserecyclerview.item.data.BaseRecyclerViewItemData

abstract class ChoosableItemViewProviderProducer<
    ContentItemDataType : BaseRecyclerViewItemData,
    ContentItemViewProviderType: BaseRecyclerViewItemViewProvider<ContentItemDataType>
>(
    context: Context
) : BaseRecyclerViewItemViewProviderProducer<
    ContentItemDataType,
    ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType>
>() {
    @DrawableRes
    private var mLeftSwipeIcon: Int = 0
    @DrawableRes
    private var mRightSwipeIcon: Int = 0
    @ColorInt
    private var mLeftSwipeIconColor: Int = 0
    @ColorInt
    private var mRightSwipeIconColor: Int = 0
    @ColorInt
    private var mLeftSwipeTextColor: Int = 0
    @ColorInt
    private var mRightSwipeTextColor: Int = 0
    private var mLeftSwipeText: String = String()
    private var mRightSwipeText: String = String()

    @ColorInt
    private var mLeftSwipeBackgroundColor: Int = 0
    @ColorInt
    private var mRightSwipeBackgroundColor: Int = 0

    private var mHintIconSize: Int = 0

    init {
        initValues(context)
    }

    private fun initValues(context: Context) {
        mLeftSwipeIcon = context.theme.resolveDrawableAttr(R.attr.choosableListItemLeftSwipeHintViewIcon)
        mRightSwipeIcon = context.theme.resolveDrawableAttr(R.attr.choosableListItemRightSwipeHintViewIcon)
        mLeftSwipeIconColor = context.theme.resolveColorAttr(R.attr.choosableListItemLeftSwipeHintViewIconTint)
        mRightSwipeIconColor = context.theme.resolveColorAttr(R.attr.choosableListItemRightSwipeHintViewIconTint)
        mLeftSwipeTextColor = context.theme.resolveColorAttr(R.attr.choosableListItemLeftSwipeHintViewTextColor)
        mRightSwipeTextColor = context.theme.resolveColorAttr(R.attr.choosableListItemRightSwipeHintViewTextColor)
        mLeftSwipeText = context.theme.resolveStringAttr(R.attr.choosableListItemLeftSwipeHintViewText)
        mRightSwipeText = context.theme.resolveStringAttr(R.attr.choosableListItemRightSwipeHintViewText)

        mLeftSwipeBackgroundColor = context.theme
            .resolveColorAttr(R.attr.choosableListItemLeftSwipeBackgroundColor)
        mRightSwipeBackgroundColor = context.theme
            .resolveColorAttr(R.attr.choosableListItemRightSwipeBackgroundColor)

        mHintIconSize = context.theme.resolveDimenAttr(R.attr.choosableListItemHintIconSize)!!.toInt()
    }

    protected fun createChoosableItemView(
        parent: ViewGroup, contentItemViewProvider: ContentItemViewProviderType
    ): ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType> {
        val divider = createDivider(parent.context)

        return ChoosableItemViewProvider(
            parent.context, null, contentItemViewProvider, divider
        ).apply {
            setLeftHintContent(getLeftSwipingHintView())
            setRightHintContent(getRightSwipingHintView())

            setLeftSwipeBackgroundColor(mLeftSwipeBackgroundColor)
            setRightSwipeBackgroundColor(mRightSwipeBackgroundColor)
        }
    }

    protected open fun createDivider(context: Context): MaterialDivider? {
        return null
    }

    protected fun setLeftHintContent(leftHintView: SwipeHintView) {
        leftHintView.setContent(
            mLeftSwipeIcon, mLeftSwipeIconColor, mLeftSwipeText, mLeftSwipeTextColor, mHintIconSize)
    }

    protected fun setRightHintContent(leftHintView: SwipeHintView) {
        leftHintView.setContent(
            mRightSwipeIcon, mRightSwipeIconColor, mRightSwipeText, mRightSwipeTextColor, mHintIconSize)
    }
}