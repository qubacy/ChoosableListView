package com.qubacy.choosablelistview.component.list.adapter.producer

import android.content.Context
import android.view.ViewGroup
import com.google.android.material.divider.MaterialDivider
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProviderProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.choosablelistview.component.list.item.content.StringContentItemView
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData

class StringItemViewProducer(
    context: Context
) : ChoosableItemViewProviderProducer<StringContentItemData, StringContentItemView>(context) {
    override fun createDivider(context: Context): MaterialDivider {
        return MaterialDivider(context)
    }

    override fun createItemViewProvider(
        parent: ViewGroup, viewType: Int)
    : ChoosableItemViewProvider<StringContentItemData, StringContentItemView> {
        val contentItemView = StringContentItemView(parent.context, null)
        val itemView = createChoosableItemView(parent, contentItemView)

        return itemView
    }
}