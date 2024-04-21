package com.qubacy.choosablelistview.component.list.item.content._test

import android.content.Context
import android.view.View
import com.google.android.material.textview.MaterialTextView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider

class TestItemContentView(
    context: Context
) : BaseRecyclerViewItemViewProvider<TestItemContentViewData>, MaterialTextView(context) {
    override fun setData(contentItemData: TestItemContentViewData) {
        text = contentItemData.text
    }

    override fun getView(): View {
        return this
    }
}