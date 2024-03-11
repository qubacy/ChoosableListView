package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.databinding.ComponentListItemBinding

class StringListAdapter(

) : RecyclerView.Adapter<StringListAdapter.StringListItemViewHolder>() {
    companion object {
        const val TAG = "StringListAdapter"
    }

    class StringListItemViewHolder(
        val itemBinding: ComponentListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun setData(text: String) {
            itemBinding.componentListItemText.text = text
        }
    }

    private val mItems: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringListItemViewHolder {
        val viewHolderBinding = ComponentListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return StringListItemViewHolder(viewHolderBinding)
    }

    override fun onBindViewHolder(holder: StringListItemViewHolder, position: Int) {
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

    fun addItem(item: String) {
        mItems.add(item)

        notifyItemInserted(mItems.size - 1)
    }

    fun setItems(items: List<String>) {
        mItems.apply {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }
}