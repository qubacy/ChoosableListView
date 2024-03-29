package com.qubacy.choosablelistviewlib.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.choosablelistviewlib.adapter.producer.ChoosableItemViewProducer
import com.qubacy.choosablelistviewlib.item.ChoosableItemView
import com.qubacy.choosablelistviewlib.item.content.ChoosableItemContentView
import com.qubacy.choosablelistviewlib.item.content.data.ChoosableItemContentViewData

abstract class ChoosableListAdapter<
    ContentItemViewType, ContentItemDataType : ChoosableItemContentViewData,
    ViewHolderType : ChoosableListAdapter.ChoosableListItemViewHolder<ContentItemViewType, ContentItemDataType>
>(
    val itemViewProducer: ChoosableItemViewProducer<ContentItemViewType, ContentItemDataType>
) : RecyclerView.Adapter<ChoosableListAdapter.ChoosableListItemViewHolder<
        ContentItemViewType, ContentItemDataType>
>(

) where ContentItemViewType : ChoosableItemContentView<ContentItemDataType>, ContentItemViewType : View {
    companion object {
        const val TAG = "ChoosableListAdapter"
    }

    abstract class ChoosableListItemViewHolder<
        ContentViewType, ContentItemDataType : ChoosableItemContentViewData
    >(
        open val choosableItemView: ChoosableItemView<ContentViewType, ContentItemDataType>
    ) : RecyclerView.ViewHolder(
        choosableItemView
    ) where ContentViewType : View, ContentViewType : ChoosableItemContentView<ContentItemDataType> {
        fun setData(contentItemData: ContentItemDataType) {
            choosableItemView.contentView.setData(contentItemData)
        }
    }

    private val mItems: MutableList<ContentItemDataType> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChoosableListItemViewHolder<ContentItemViewType, ContentItemDataType> {
        val itemView = itemViewProducer.createItemView(parent, viewType)

        return createViewHolder(itemView)
    }

    abstract fun createViewHolder(
        itemView: ChoosableItemView<ContentItemViewType, ContentItemDataType>
    ): ViewHolderType

    override fun onBindViewHolder(
        holder: ChoosableListItemViewHolder<ContentItemViewType, ContentItemDataType>,
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