package com.qubacy.choosablelistview.component.list.adapter.producer._test

import android.content.Context
import android.view.ViewGroup
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemView

class TestItemViewProducer(
    context: Context
) : ChoosableItemViewProducer<TestItemContentView, TestItemContentViewData>(context) {
    override fun createItemView(
        parent: ViewGroup,
        viewType: Int
    ): ChoosableItemView<TestItemContentView, TestItemContentViewData> {
        val contentItemView = TestItemContentView(parent.context)
        val itemView = createChoosableItemView(parent, contentItemView)

        return itemView
    }
}