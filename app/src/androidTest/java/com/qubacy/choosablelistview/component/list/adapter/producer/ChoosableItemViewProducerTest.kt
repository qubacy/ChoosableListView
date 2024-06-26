package com.qubacy.choosablelistview.component.list.adapter.producer

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout.LayoutParams
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.activity.scenario.util.getContext
import com.qubacy.choosablelistview.component.list.adapter.producer._test.TestItemViewProviderProducer
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoosableItemViewProducerTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mContentView: ViewGroup
    private lateinit var mProducer: TestItemViewProviderProducer

    @Before
    fun setup() {
        val activityContext = activityScenarioRule.scenario.getContext()

        prepareActivity()
        initProducer(activityContext)
    }

    private fun prepareActivity() {
        activityScenarioRule.scenario.onActivity {
            mContentView = FrameLayout(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            }

            it.setContentView(mContentView)
        }
    }

    private fun initProducer(context: Context) {
        mProducer = TestItemViewProviderProducer(context)
    }

    @Test
    fun createItemViewTest() {
        val viewType = 0
        val parent = mContentView

        val itemView = mProducer.createItemViewProvider(parent, viewType)

        activityScenarioRule.scenario.onActivity {
            mContentView.addView(itemView)
        }

        Espresso.onView(Matchers.allOf(
            ViewMatchers.isAssignableFrom(ChoosableItemViewProvider::class.java),
            ViewMatchers.hasDescendant(
                ViewMatchers.isAssignableFrom(TestItemContentView::class.java)
            )
        )).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}