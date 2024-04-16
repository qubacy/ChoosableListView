package com.qubacy.choosablelistview.component.list.adapter

import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.choosablelistview.component.list.adapter.producer.StringItemViewProducer
import com.qubacy.choosablelistview.component.list.item.content.StringContentItemView
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter

class StringListAdapter(
    itemViewProducer: StringItemViewProducer
) : ChoosableListAdapter<
    StringContentItemData,
    StringContentItemView,
    StringListAdapter.StringListItemViewHolder
>(itemViewProducer) {
    companion object {
        const val TAG = "StringListAdapter"
    }

    class StringListItemViewHolder(
        itemView: ChoosableItemViewProvider<StringContentItemData, StringContentItemView>
    ) : ChoosableListItemViewHolder<StringContentItemData, StringContentItemView>(itemView) {

    }

    override fun createViewHolder(
        itemView: ChoosableItemViewProvider<StringContentItemData, StringContentItemView>
    ): StringListItemViewHolder {
        return StringListItemViewHolder(itemView)
    }
}