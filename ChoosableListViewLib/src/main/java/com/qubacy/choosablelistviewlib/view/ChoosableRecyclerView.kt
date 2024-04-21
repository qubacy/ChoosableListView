package com.qubacy.choosablelistviewlib.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.children
import androidx.recyclerview.widget.ItemTouchHelper
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.utility.baserecyclerview._common.view.provider.ViewProvider
import com.qubacy.utility.baserecyclerview.view.BaseRecyclerView

class ChoosableRecyclerView(
    context: Context,
    attributeSet: AttributeSet? = null
) : BaseRecyclerView(context, attributeSet) {
    private lateinit var mItemTouchHelperCallback: ChoosableListItemTouchHelperCallback
    private lateinit var mItemTouchHelper: ItemTouchHelper

    fun setItemTouchHelperCallback(itemTouchHelperCallback: ChoosableListItemTouchHelperCallback) {
        mItemTouchHelperCallback = itemTouchHelperCallback
        mItemTouchHelper = ItemTouchHelper(mItemTouchHelperCallback)

        mItemTouchHelper.attachToRecyclerView(this)
    }

    override fun setChildrenEnabled(areEnabled: Boolean) {
        for (child in children)
            (child as ViewProvider).setViewProviderEnabled(areEnabled)

        mItemTouchHelperCallback.setIsSwipeEnabled(areEnabled)
    }

    fun returnSwipedItem(position: Int) {
        val viewHolder = findViewHolderForAdapterPosition(position) ?: return

        viewHolder as ChoosableListAdapter.ChoosableListItemViewHolder<*, *>

        viewHolder.baseItemViewProvider.swipeBack()

        // todo: is this OK at all?:
        mItemTouchHelper.attachToRecyclerView(null)
        mItemTouchHelper.attachToRecyclerView(this)
    }
}