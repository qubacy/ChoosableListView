package com.qubacy.choosablelistview.component.list.adapter.producer._test

import android.content.Context
import android.view.ViewGroup
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProviderProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider

class TestItemViewProviderProducer(
    context: Context
) : ChoosableItemViewProviderProducer<TestItemContentViewData, TestItemContentView>(context) {
    override fun createItemViewProvider(
        parent: ViewGroup,
        viewType: Int
    ): ChoosableItemViewProvider<TestItemContentViewData, TestItemContentView> {
        val contentItemView = TestItemContentView(parent.context)
        val itemView = createChoosableItemView(parent, contentItemView)

        return itemView
    }
}