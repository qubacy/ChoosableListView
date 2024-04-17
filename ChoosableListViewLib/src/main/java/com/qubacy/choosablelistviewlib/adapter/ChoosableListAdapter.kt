package com.qubacy.choosablelistviewlib.adapter

import androidx.annotation.UiThread
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProviderProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.utility.baserecyclerview.adapter.BaseRecyclerViewAdapter
import com.qubacy.utility.baserecyclerview.item.BaseRecyclerViewItemViewProvider
import com.qubacy.utility.baserecyclerview.item.data.BaseRecyclerViewItemData

abstract class ChoosableListAdapter<
    ContentItemDataType : BaseRecyclerViewItemData,
    ContentItemViewProviderType : BaseRecyclerViewItemViewProvider<ContentItemDataType>,
    СhoosableItemViewProviderProducerType: ChoosableItemViewProviderProducer<ContentItemDataType, ContentItemViewProviderType>,
    ViewHolderType : ChoosableListAdapter.ChoosableListItemViewHolder<ContentItemDataType, ContentItemViewProviderType>
>(
    val itemViewProducer: СhoosableItemViewProviderProducerType
) : BaseRecyclerViewAdapter<
    ContentItemDataType,
    ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType>,
    СhoosableItemViewProviderProducerType,
    ViewHolderType
>(
    itemViewProducer
) {
    companion object {
        const val TAG = "ChoosableListAdapter"
    }

    abstract class ChoosableListItemViewHolder<
        ContentItemDataType : BaseRecyclerViewItemData,
        ContentItemViewProviderType : BaseRecyclerViewItemViewProvider<ContentItemDataType>
    >(
        choosableItemView: ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType>
    ) : ViewHolder<ContentItemDataType, ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType>>(
        choosableItemView
    ) {

    }

    @UiThread
    fun removeItemAtPosition(position: Int) {
        mItems.removeAt(position)

        wrappedNotifyItemRemoved(position)
    }

    @UiThread
    fun addItem(item: ContentItemDataType) {
        mItems.add(item)

        wrappedNotifyItemInserted(mItems.size - 1)
    }

    @UiThread
    fun setItems(items: List<ContentItemDataType>) {
        mItems.apply {
            clear()
            addAll(items)
        }

        wrappedNotifyDataSetChanged()
    }
}