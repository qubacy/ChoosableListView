package com.qubacy.choosablelistviewlib.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.ItemTouchHelper
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.utility.baserecyclerview.view.BaseRecyclerView

class ChoosableRecyclerView(
    context: Context,
    attributeSet: AttributeSet
) : BaseRecyclerView(context, attributeSet) {
    private lateinit var mItemTouchHelperCallback: ChoosableListItemTouchHelperCallback

    fun setItemTouchHelperCallback(itemTouchHelperCallback: ChoosableListItemTouchHelperCallback) {
        mItemTouchHelperCallback = itemTouchHelperCallback

        val itemTouchHelper = ItemTouchHelper(mItemTouchHelperCallback)

        itemTouchHelper.attachToRecyclerView(this)
    }

    override fun setChildrenEnabled(areEnabled: Boolean) {
        super.setChildrenEnabled(areEnabled)

        mItemTouchHelperCallback.setIsSwipeEnabled(areEnabled)
    }
}