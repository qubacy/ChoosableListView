package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content

import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.data.ChoosableItemContentViewData

interface ChoosableItemContentView<ContentItemDataType : ChoosableItemContentViewData> {
    fun setData(contentItemData: ContentItemDataType)
}