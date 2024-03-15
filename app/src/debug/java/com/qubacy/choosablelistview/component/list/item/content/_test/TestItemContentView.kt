package com.qubacy.choosablelistview.component.list.item.content._test

import android.content.Context
import android.view.View
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib.item.content.ChoosableItemContentView

class TestItemContentView(
    context: Context
) : ChoosableItemContentView<TestItemContentViewData>, View(context) {
    override fun setData(contentItemData: TestItemContentViewData) { }
}