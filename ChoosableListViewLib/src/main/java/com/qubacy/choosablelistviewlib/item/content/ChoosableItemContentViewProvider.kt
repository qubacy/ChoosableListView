package com.qubacy.choosablelistviewlib.item.content

import com.qubacy.choosablelistviewlib._common.view.provider.ViewProvider
import com.qubacy.choosablelistviewlib.item.content.data.ChoosableItemContentViewData

interface ChoosableItemContentViewProvider<ContentItemDataType : ChoosableItemContentViewData> : ViewProvider {
    fun setData(contentItemData: ContentItemDataType)
}