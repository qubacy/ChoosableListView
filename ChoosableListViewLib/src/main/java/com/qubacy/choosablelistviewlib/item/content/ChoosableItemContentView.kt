package com.qubacy.choosablelistviewlib.item.content

import com.qubacy.choosablelistviewlib.item.content.data.ChoosableItemContentViewData

interface ChoosableItemContentView<ContentItemDataType : ChoosableItemContentViewData> {
    fun setData(contentItemData: ContentItemDataType)
}