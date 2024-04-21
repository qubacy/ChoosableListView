package com.qubacy.choosablelistview.component.list.view

import android.content.Context
import android.view.View
import androidx.core.view.get
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.activity.scenario.util.getContext
import com.qubacy.choosablelistview._common._test.view.util.action.swipe.SwipeViewActionUtil
import com.qubacy.choosablelistview._common._test.view.util.action.wait.WaitViewAction
import com.qubacy.choosablelistview.component.list.adapter._test.TestListAdapter
import com.qubacy.choosablelistview.component.list.adapter.producer._test.TestItemViewProviderProducer
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.choosablelistviewlib.item.animator.ChoosableListItemAnimator
import com.qubacy.choosablelistviewlib.view.ChoosableRecyclerView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoosableRecyclerViewTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mAdapter: TestListAdapter
    private lateinit var mListView: ChoosableRecyclerView

    @Before
    fun setup() {
        val activityContext = activityScenarioRule.scenario.getContext()

        initListView(activityContext)
        prepareActivity()
    }

    private fun initListView(context: Context) {
        mListView = ChoosableRecyclerView(context)
    }

    private fun prepareActivity() {
        activityScenarioRule.scenario.onActivity {
            it.setContentView(mListView)
            prepareListView(it)
        }
    }

    private fun prepareListView(context: Context) {
        mAdapter = TestListAdapter(TestItemViewProviderProducer(context))

        val itemTouchHelper = ChoosableListItemTouchHelperCallback(mCallback =
        object : ChoosableListItemTouchHelperCallback.Callback {
            override fun onItemSwiped(direction: SwipeDirection, position: Int) { }
        }
        )

        mListView.apply {
            adapter = mAdapter
            itemAnimator = ChoosableListItemAnimator()
        }

        mListView.setItemTouchHelperCallback(itemTouchHelper)
    }

    @Test
    fun returnSwipedItemTest() {
        val initItem = TestItemContentViewData()

        activityScenarioRule.scenario.onActivity {
            mAdapter.addItem(initItem)
        }

        Espresso.onView(isRoot()).perform(WaitViewAction(2000))
        Espresso.onView(isAssignableFrom(TestItemContentView::class.java))
            .perform(SwipeViewActionUtil.generateSwipeViewAction(0f, 0f))
    }
}