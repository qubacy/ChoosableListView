package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.adapter.producer

import android.content.Context
import android.view.ViewGroup
import com.google.android.material.divider.MaterialDivider
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.adapter.producer.ChoosableItemViewProducer
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.ChoosableItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.StringContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.data.StringContentItemData

class StringItemViewProducer(
    context: Context
) : ChoosableItemViewProducer<StringContentItemView, StringContentItemData>(context) {
    /**
     * Use createChoosableItemView() to obtain a ChoosableItemView instance;
     */
    override fun createItemView(
        parent: ViewGroup,
        viewType: Int
    ): ChoosableItemView<StringContentItemView, StringContentItemData> {
        val contentItemView = StringContentItemView(parent.context, null)
        val itemView = createChoosableItemView(parent, contentItemView)

        return itemView
    }

    override fun createDivider(context: Context): MaterialDivider {
        return MaterialDivider(context)
    }
}