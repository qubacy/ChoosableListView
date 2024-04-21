package com.qubacy.choosablelistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistview.databinding.ActivityMainBinding
import com.qubacy.choosablelistviewlib.item.animator.ChoosableListItemAnimator
import com.qubacy.choosablelistview.component.list.adapter.StringListAdapter
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.choosablelistview.component.list.adapter.producer.StringItemViewProducer
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData
import com.qubacy.choosablelistviewlib.view.ChoosableRecyclerView

class MainActivity : AppCompatActivity(), ChoosableListItemTouchHelperCallback.Callback {
    companion object {
        val LIST_ITEMS = listOf(
            StringContentItemData("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
            StringContentItemData("Tristique et egestas quis ipsum suspendisse ultrices gravida dictum. Amet mauris commodo quis imperdiet massa tincidunt nunc. Consequat mauris nunc congue nisi vitae suscipit tellus. Lacus luctus accumsan tortor posuere ac ut consequat semper viverra."),
            StringContentItemData("Turpis egestas pretium aenean pharetra magna ac placerat. Potenti nullam ac tortor vitae purus faucibus. Arcu cursus euismod quis viverra nibh cras. Massa sapien faucibus et molestie. Nibh cras pulvinar mattis nunc sed blandit. Non odio euismod lacinia at quis risus sed vulputate. Aliquam faucibus purus in massa tempor nec feugiat.")
        )
    }

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: StringListAdapter

    private var mLastIsListEnabled: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
        initList()

        mBinding.buttonAdd.setOnClickListener { onAddClicked() }
        mBinding.buttonChangeEnabled.setOnClickListener { onChangeEnabledClicked() }
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
        mBinding.list.apply {
            adapter = listAdapter
            itemAnimator = ChoosableListItemAnimator().apply {
                removeDuration = 200
            }
        }
    }

    private fun initListItemTouchHelper(listView: ChoosableRecyclerView) {
        val itemTouchHelperCallback =
            ChoosableListItemTouchHelperCallback(mCallback = this)

        listView.setItemTouchHelperCallback(itemTouchHelperCallback)
    }

    private fun onAddClicked() {
        mAdapter.addItem(LIST_ITEMS.last())
    }

    private fun onChangeEnabledClicked() {
        mLastIsListEnabled = !mLastIsListEnabled

        mBinding.list.setIsEnabled(mLastIsListEnabled)
    }

    override fun onItemSwiped(
        direction: SwipeDirection,
        position: Int
    ) {
        //mAdapter.removeItemAtPosition(position)
        startItemReturnTimer(position)
    }

    private fun startItemReturnTimer(position: Int) {
        object : CountDownTimer(3000, 3000) {
            override fun onTick(millisUntilFinished: Long) {  }

            override fun onFinish() {
                mBinding.list.returnSwipedItem(position)
            }
        }.start()
    }
}