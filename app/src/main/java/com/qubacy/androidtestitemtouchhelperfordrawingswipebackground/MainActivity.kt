package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list._common.SwipeDirection
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.databinding.ActivityMainBinding
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.animator.SmoothListItemAnimator
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.adapter.StringListAdapter
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.helper.StringListItemTouchHelperCallback
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.adapter.producer.StringItemViewProducer
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.component.list.item.content.data.StringContentItemData
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util.resolveColorAttr

class MainActivity : AppCompatActivity(), StringListItemTouchHelperCallback.Callback {
    companion object {
        val LIST_ITEMS = listOf(
            StringContentItemData("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            StringContentItemData("Tristique et egestas quis ipsum suspendisse ultrices gravida dictum. Amet mauris commodo quis imperdiet massa tincidunt nunc. Consequat mauris nunc congue nisi vitae suscipit tellus. Lacus luctus accumsan tortor posuere ac ut consequat semper viverra."),
            StringContentItemData("Turpis egestas pretium aenean pharetra magna ac placerat. Potenti nullam ac tortor vitae purus faucibus. Arcu cursus euismod quis viverra nibh cras. Massa sapien faucibus et molestie. Nibh cras pulvinar mattis nunc sed blandit. Non odio euismod lacinia at quis risus sed vulputate. Aliquam faucibus purus in massa tempor nec feugiat.")
        )
    }

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: StringListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
        initList()

        mBinding.buttonAdd.setOnClickListener { onAddClicked() }
    }

    private fun initList() {
        val listItems = initListData()

        mAdapter = initListAdapter(listItems)
        initListView(mAdapter)
        initListItemTouchHelper(mBinding.list)
    }

    private fun initListData(): MutableList<StringContentItemData> {
        return LIST_ITEMS.toMutableList()
    }

    private fun initListAdapter(items: List<StringContentItemData>): StringListAdapter {
        val itemProducer = StringItemViewProducer(this)

        return StringListAdapter(itemProducer).apply {
            setItems(items)
        }
    }

    private fun initListView(listAdapter: StringListAdapter) {
        val decorationDivider = MaterialDividerItemDecoration(
            this@MainActivity, MaterialDividerItemDecoration.HORIZONTAL
        ).apply {
            dividerColor = theme.resolveColorAttr(
                com.google.android.material.R.attr.colorOutlineVariant)
        }

        mBinding.list.apply {
            addItemDecoration(decorationDivider)

            adapter = listAdapter
            itemAnimator = SmoothListItemAnimator()
        }
    }

    private fun initListItemTouchHelper(listView: RecyclerView) {
        val itemTouchHelperCallback = StringListItemTouchHelperCallback(
            mCallback = this
        )
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)

        itemTouchHelper.attachToRecyclerView(listView)
    }

    private fun onAddClicked() {
        mAdapter.addItem(LIST_ITEMS.last())
    }

    override fun onItemSwiped(
        direction: SwipeDirection,
        position: Int
    ) {
        mAdapter.removeItemAtPosition(position)
    }
}