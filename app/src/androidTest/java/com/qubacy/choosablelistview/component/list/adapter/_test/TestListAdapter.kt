package com.qubacy.choosablelistview.component.list.adapter._test

import com.qubacy.choosablelistview.component.list.adapter.producer._test.TestItemViewProviderProducer
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider

class TestListAdapter(
    itemViewProducer: TestItemViewProviderProducer
) : ChoosableListAdapter<
    TestItemContentViewData,
    TestItemContentView,
    TestItemViewProviderProducer,
    TestListAdapter.ViewHolder
>(itemViewProducer) {
    companion object {
        const val TAG = "StringListAdapter"
    }

    class ViewHolder(
        itemView: ChoosableItemViewProvider<TestItemContentViewData, TestItemContentView>
    ) : ChoosableListItemViewHolder<TestItemContentViewData, TestItemContentView>(
        itemView
    ) {

    }

    override fun createViewHolder(
        itemView: ChoosableItemViewProvider<TestItemContentViewData, TestItemContentView>
    ): ViewHolder {
        return ViewHolder(itemView)
    }
}