package com.qubacy.choosablelistview.component.list.item.content._test

import android.content.Context
import android.view.View
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider

class TestItemContentView(
    context: Context
) : BaseRecyclerViewItemViewProvider<TestItemContentViewData>, View(context) {
    override fun setData(contentItemData: TestItemContentViewData) { }
    override fun getView(): View {
        return this
    }
}