package com.qubacy.choosablelistview._common.component.list.item.content

import com.qubacy.choosablelistview._common.component.list.item.content.data.ChoosableItemContentViewData

interface ChoosableItemContentView<ContentItemDataType : ChoosableItemContentViewData> {
    fun setData(contentItemData: ContentItemDataType)
}