package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content

import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.data.ContentItemData

interface ContentItemView<ContentItemDataType : ContentItemData> {
    fun setData(contentItemData: ContentItemDataType)
}