package com.qubacy.choosablelistview.component.list.adapter

import com.qubacy.choosablelistview._common.component.list.adapter.ChoosableListAdapter
import com.qubacy.choosablelistview._common.component.list.item.ChoosableItemView
import com.qubacy.choosablelistview.component.list.adapter.producer.StringItemViewProducer
import com.qubacy.choosablelistview.component.list.item.content.StringContentItemView
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData

class StringListAdapter(
    itemViewProducer: StringItemViewProducer
) : ChoosableListAdapter<
    StringContentItemView, StringContentItemData,
    StringListAdapter.StringListItemViewHolder
>(itemViewProducer) {
    companion object {
        const val TAG = "StringListAdapter"
    }

    class StringListItemViewHolder(
        itemView: ChoosableItemView<StringContentItemView, StringContentItemData>
    ) : ChoosableListItemViewHolder<StringContentItemView, StringContentItemData>(itemView) {

    }

    override fun createViewHolder(
        itemView: ChoosableItemView<StringContentItemView, StringContentItemData>
    ): StringListItemViewHolder {
        return StringListItemViewHolder(itemView)
    }
}