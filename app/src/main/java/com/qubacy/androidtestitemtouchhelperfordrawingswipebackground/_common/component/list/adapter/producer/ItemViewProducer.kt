package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.adapter.producer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.R
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.ChoosableItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.ContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.data.ContentItemData
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.hint.SwipeHintView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util.resolveColorAttr
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util.resolveDrawableAttr
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util.resolveStringAttr

abstract class ItemViewProducer<
    ContentViewType, ContentItemDataType : ContentItemData
>(
    context: Context
) where ContentViewType : View, ContentViewType : ContentItemView<ContentItemDataType> {
    @DrawableRes
    private var mLeftImage: Int = 0
    @DrawableRes
    private var mRightImage: Int = 0
    @ColorInt
    private var mLeftImageColor: Int = 0
    @ColorInt
    private var mRightImageColor: Int = 0
    @ColorInt
    private var mLeftTextColor: Int = 0
    @ColorInt
    private var mRightTextColor: Int = 0
    private var mLeftText: String = String()
    private var mRightText: String = String()

    @ColorInt
    private var mLeftBackgroundColor: Int = 0
    @ColorInt
    private var mRightBackgroundColor: Int = 0

    init {
        initValues(context)
    }

    private fun initValues(context: Context) {
        mLeftImage = context.theme.resolveDrawableAttr(R.attr.leftSwipeHintViewIcon)
        mRightImage = context.theme.resolveDrawableAttr(R.attr.rightSwipeHintViewIcon)
        mLeftImageColor = context.theme.resolveColorAttr(R.attr.leftSwipeHintViewIconTint)
        mRightImageColor = context.theme.resolveColorAttr(R.attr.rightSwipeHintViewIconTint)
        mLeftTextColor = context.theme.resolveColorAttr(R.attr.leftSwipeHintViewTextColor)
        mRightTextColor = context.theme.resolveColorAttr(R.attr.rightSwipeHintViewTextColor)
        mLeftText = context.theme.resolveStringAttr(R.attr.leftSwipeHintViewText)
        mRightText = context.theme.resolveStringAttr(R.attr.rightSwipeHintViewText)

        mLeftBackgroundColor = context.theme
            .resolveColorAttr(R.attr.choosableItemViewLeftSwipeBackgrouncColor)
        mRightBackgroundColor = context.theme
            .resolveColorAttr(R.attr.choosableItemViewRightSwipeBackgrouncColor)
    }

    abstract fun createItemView(
        parent: ViewGroup, viewType: Int
    ): ChoosableItemView<ContentViewType, ContentItemDataType>

    protected fun createChoosableItemView(
        parent: ViewGroup, contentItemView: ContentViewType
    ): ChoosableItemView<ContentViewType, ContentItemDataType> {
        return ChoosableItemView(parent.context, null, contentItemView).apply {
            setLeftHintContent(getLeftSwipingHintView())
            setRightHintContent(getRightSwipingHintView())
            setLeftSwipeBackgroundColor(mLeftBackgroundColor)
            setRightSwipeBackgroundColor(mRightBackgroundColor)
        }
    }

    protected fun setLeftHintContent(leftHintView: SwipeHintView) {
        leftHintView.setContent(mLeftImage, mLeftImageColor, mLeftText, mLeftTextColor)
    }

    protected fun setRightHintContent(leftHintView: SwipeHintView) {
        leftHintView.setContent(mRightImage, mRightImageColor, mRightText, mRightTextColor)
    }
}