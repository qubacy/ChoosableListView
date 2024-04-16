package com.qubacy.choosablelistviewlib.adapter

import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProviderProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.choosablelistviewlib.item.content.ChoosableItemContentViewProvider
import com.qubacy.choosablelistviewlib.item.content.data.ChoosableItemContentViewData

abstract class ChoosableListAdapter<
    ContentItemDataType : ChoosableItemContentViewData,
    ContentItemViewProviderType : ChoosableItemContentViewProvider<ContentItemDataType>,
    ViewHolderType : ChoosableListAdapter.ChoosableListItemViewHolder<ContentItemDataType, ContentItemViewProviderType>
>(
    val itemViewProducer: ChoosableItemViewProviderProducer<ContentItemDataType, ContentItemViewProviderType>
) : RecyclerView.Adapter<ChoosableListAdapter.ChoosableListItemViewHolder<
    ContentItemDataType, ContentItemViewProviderType
>>(

) {
    companion object {
        const val TAG = "ChoosableListAdapter"
    }

    abstract class ChoosableListItemViewHolder<
        ContentItemDataType : ChoosableItemContentViewData,
        ContentViewProviderType : ChoosableItemContentViewProvider<ContentItemDataType>
    >(
        open val choosableItemView: ChoosableItemViewProvider<ContentItemDataType, ContentViewProviderType>
    ) : RecyclerView.ViewHolder(
        choosableItemView
    ) {
        fun setData(contentItemData: ContentItemDataType) {
            choosableItemView.contentViewProvider.setData(contentItemData)
        }
    }

    private val mItems: MutableList<ContentItemDataType> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChoosableListItemViewHolder<ContentItemDataType, ContentItemViewProviderType> {
        val itemView = itemViewProducer.createItemView(parent, viewType)

        return createViewHolder(itemView)
    }

    abstract fun createViewHolder(
        itemView: ChoosableItemViewProvider<ContentItemDataType, ContentItemViewProviderType>
    ): ViewHolderType

    override fun onBindViewHolder(
        holder: ChoosableListItemViewHolder<ContentItemDataType, ContentItemViewProviderType>,
        position: Int
    ) {
        val item = mItems[position]

        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
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

    open fun wrappedNotifyItemRemoved(position: Int) {
        notifyItemRemoved(position)
    }

    open fun wrappedNotifyItemInserted(position: Int) {
        notifyItemInserted(position)
    }

    open fun wrappedNotifyDataSetChanged() {
        notifyDataSetChanged()
    }
}