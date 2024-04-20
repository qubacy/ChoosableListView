package com.qubacy.choosablelistview.component.list.item.content

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.setPadding
import com.google.android.material.textview.MaterialTextView
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData
import com.qubacy.choosablelistviewlib._common.util.resolveColorAttr
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider

class StringContentItemView(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs),
    BaseRecyclerViewItemViewProvider<StringContentItemData> {
    companion object {
        const val TAG = "StringContentItemView"

        const val DEFAULT_PADDING_PX = 20
    }

    private val mTextView: MaterialTextView

    init {
        mTextView = MaterialTextView(context, attrs)

        initLayout()
        setAppearance()
    }

    private fun initLayout() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

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

    override fun getView(): View {
        return this
    }

    override fun setViewProviderEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled
    }
}