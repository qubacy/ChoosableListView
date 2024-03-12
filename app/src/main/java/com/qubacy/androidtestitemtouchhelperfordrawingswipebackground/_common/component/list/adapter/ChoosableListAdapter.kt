package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.ChoosableItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.ContentItemView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.content.data.ContentItemData

abstract class ChoosableListAdapter<
    ContentItemViewType, ContentItemDataType : ContentItemData
>(

) : RecyclerView.Adapter<ChoosableListAdapter.ChoosableListItemViewHolder<
        ContentItemViewType, ContentItemDataType>
>(

) where ContentItemViewType : ContentItemView<ContentItemDataType>, ContentItemViewType : View {
    companion object {
        const val TAG = "ChoosableListAdapter"
    }

    abstract class ChoosableListItemViewHolder<
        ContentViewType, ContentItemDataType : ContentItemData
    >(
        val choosableItemView: ChoosableItemView<ContentViewType, ContentItemDataType>
    ) : RecyclerView.ViewHolder(
        choosableItemView
    ) where ContentViewType : View, ContentViewType : ContentItemView<ContentItemDataType> {
        fun setData(contentItemData: ContentItemDataType) {
            choosableItemView.contentView.setData(contentItemData)
        }
    }

    private val mItems: MutableList<ContentItemDataType> = mutableListOf()

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

    fun removeItemAtPosition(position: Int) {
        mItems.removeAt(position)

        notifyItemRemoved(position)
    }

    fun addItem(item: ContentItemDataType) {
        mItems.add(item)

        notifyItemInserted(mItems.size - 1)
    }

    fun setItems(items: List<ContentItemDataType>) {
        mItems.apply {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }
}