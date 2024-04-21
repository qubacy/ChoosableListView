package com.qubacy.choosablelistview.component.list.view

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.activity.scenario.util.getContext
import com.qubacy.choosablelistview._common._test.view.util.action.recyclerview.scroll.ScrollRecyclerViewViewAction
import com.qubacy.choosablelistview._common._test.view.util.action.swipe.SwipeViewActionUtil
import com.qubacy.choosablelistview._common._test.view.util.action.wait.WaitViewAction
import com.qubacy.choosablelistview.component.list.adapter._test.TestListAdapter
import com.qubacy.choosablelistview.component.list.adapter.producer._test.TestItemViewProviderProducer
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistviewlib._common.util.resolveIntegerAttr
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.choosablelistviewlib.item.animator.ChoosableListItemAnimator
import com.qubacy.choosablelistviewlib.view.ChoosableRecyclerView
import com.qubacy.choosablelistviewlib.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoosableRecyclerViewTest {
    companion object {
        val DEFAULT_TEST_ITEM_DATA = TestItemContentViewData("test")
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private var mSwipeBackAnimationDuration: Long = 0L

    private lateinit var mAdapter: TestListAdapter
    private lateinit var mListView: ChoosableRecyclerView

    @Before
    fun setup() {
        val activityContext = activityScenarioRule.scenario.getContext()

        initValues(activityContext)
        initListView(activityContext)
        prepareActivity()
        setupItemTouchHelperCallback()
    }

    private fun initValues(context: Context) {
        mSwipeBackAnimationDuration = context.theme.resolveIntegerAttr(
            R.attr.choosableListItemSwipeBackAnimationDuration).toLong()
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

        mListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            itemAnimator = ChoosableListItemAnimator()
        }
    }

    private fun setupItemTouchHelperCallback(
        onItemSwiped: ((position: Int) -> Unit)? = null
    ) {
        val itemTouchHelper = ChoosableListItemTouchHelperCallback(mCallback =
            object : ChoosableListItemTouchHelperCallback.Callback {
                override fun onItemSwiped(direction: SwipeDirection, position: Int) {
                    onItemSwiped?.invoke(position)
                }
            })

        activityScenarioRule.scenario.onActivity {
            mListView.setItemTouchHelperCallback(itemTouchHelper)
        }
    }

    @Test
    fun returnSwipedItemTest() {
        val initItems = generateItems(10)

        setupItemTouchHelperCallback {
            mListView.returnSwipedItem(it)
        }
        activityScenarioRule.scenario.onActivity {
            mAdapter.setItems(initItems)
        }

        // todo: added 'cause of a sync. issue:
        Espresso.onView(isRoot()).perform(WaitViewAction(500))

        for (itemIndex in initItems.indices) {
            val item = initItems[itemIndex]

            Espresso.onView(isAssignableFrom(RecyclerView::class.java))
                .perform(ScrollRecyclerViewViewAction(itemIndex))
            Espresso.onView(withText(item.text))
                .perform(SwipeViewActionUtil.generateSwipeViewAction(
                    endCoordsProvider = GeneralLocation.CENTER_LEFT))
        }

        Espresso.onView(isRoot()).perform(WaitViewAction(mSwipeBackAnimationDuration))

        for (itemIndex in initItems.indices.reversed()) {
            val item = initItems[itemIndex]

            Espresso.onView(isAssignableFrom(RecyclerView::class.java))
                .perform(ScrollRecyclerViewViewAction(itemIndex))
            Espresso.onView(withText(item.text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

    private fun generateItems(count: Int): List<TestItemContentViewData> {
        return IntRange(0, count - 1).map {
            TestItemContentViewData("test $it")
        }
    }
}