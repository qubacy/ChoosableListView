package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.setPadding
import com.google.android.material.textview.MaterialTextView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.ContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.data.StringContentItemData
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util.resolveColorAttr

class StringContentItemView(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs), ContentItemView<StringContentItemData> {
    companion object {
        const val DEFAULT_PADDING_PX = 20
    }

    private val mTextView: MaterialTextView

    init {
        mTextView = MaterialTextView(context, attrs)

        initLayout()
        setAppearance()
    }

    private fun initLayout() {
        addView(mTextView)
        setPadding(DEFAULT_PADDING_PX)
    }

    override fun setData(contentItemData: StringContentItemData) {
        mTextView.text = contentItemData.text
    }

    private fun setAppearance() {
        val backgroundColor = context.theme
            .resolveColorAttr(com.google.android.material.R.attr.colorSurface)

        setBackgroundColor(backgroundColor)
    }
}