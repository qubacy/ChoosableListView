package com.qubacy.choosablelistview.component.list.item

import android.graphics.drawable.ColorDrawable
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.activity.scenario.util.getContext
import com.qubacy.choosablelistview._common._test.view.util.matcher.position.TranslationViewMatcher
import com.qubacy.choosablelistview._common._test.view.util.matcher.scale.ScaleViewMatcher
import com.qubacy.choosablelistview.component.list.item.content._test.TestItemContentView
import com.qubacy.choosablelistview.component.list.item.content.data._test.TestItemContentViewData
import com.qubacy.choosablelistviewlib.item.ChoosableItemViewProvider
import com.qubacy.choosablelistview.R
import com.qubacy.choosablelistview._common._test.view.util.matcher.background.BackgroundViewMatcher
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChoosableItemViewTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mItemView: ChoosableItemViewProvider<TestItemContentViewData, TestItemContentView>

    @Before
    fun setup() {
        initItemView()
        prepareActivity()
    }

    private fun initItemView() {
        val context = activityScenarioRule.scenario.getContext()
        val itemContentView = TestItemContentView(context)

        mItemView = ChoosableItemViewProvider(context, null, itemContentView)
    }

    private fun prepareActivity() {
        activityScenarioRule.scenario.onActivity {
            it.setContentView(mItemView)
        }
    }

    @Test
    fun resetViewTest() {
        val expectedEndScale = 1f
        val expectedEndTranslationY = 0
        val expectedEndContentTranslationX = 0

        val startScale = 0f
        val startTranslationY = -(mItemView.measuredHeight)
        val startContentTranslationX = mItemView.measuredWidth

        activityScenarioRule.scenario.onActivity {
            mItemView.apply {
                scaleY = startScale
                translationY = startTranslationY.toFloat()

                contentViewProvider.translationX = startContentTranslationX.toFloat()
            }
        }

        Espresso.onView(ViewMatchers.isAssignableFrom(ChoosableItemViewProvider::class.java))
            .check(ViewAssertions.matches(
                Matchers.allOf(
                    ScaleViewMatcher(scaleY = startScale),
                    TranslationViewMatcher(translationY = startTranslationY)
                )
            ))
        Espresso.onView(ViewMatchers.isAssignableFrom(TestItemContentView::class.java))
            .check(ViewAssertions.matches(
                TranslationViewMatcher(translationX = startContentTranslationX)))


        activityScenarioRule.scenario.onActivity {
            mItemView.resetView()
        }

        Espresso.onView(ViewMatchers.isAssignableFrom(ChoosableItemViewProvider::class.java))
            .check(ViewAssertions.matches(
                Matchers.allOf(
                    ScaleViewMatcher(scaleY = expectedEndScale),
                    TranslationViewMatcher(translationY = expectedEndTranslationY)
                )
            ))
        Espresso.onView(ViewMatchers.isAssignableFrom(TestItemContentView::class.java))
            .check(ViewAssertions.matches(
                TranslationViewMatcher(translationX = expectedEndContentTranslationX)))
    }

    @Test
    fun prepareForSwipeDirectionTest() {
        val expectedRightSwipeBackgroundColor = R.color.black
        val expectedLeftSwipeBackgroundColor = R.color.white

        val expectedRightSwipeBackground = ColorDrawable()
            .apply { color = expectedRightSwipeBackgroundColor }
        val expectedLeftSwipeBackground = ColorDrawable()
            .apply { color = expectedLeftSwipeBackgroundColor }

        activityScenarioRule.scenario.onActivity {
            mItemView.setRightSwipeBackgroundColor(expectedRightSwipeBackgroundColor)
            mItemView.setLeftSwipeBackgroundColor(expectedLeftSwipeBackgroundColor)
        }

        activityScenarioRule.scenario.onActivity {
            mItemView.prepareForSwipeDirection(SwipeDirection.RIGHT)
        }

        Espresso.onView(ViewMatchers.isAssignableFrom(ChoosableItemViewProvider::class.java))
            .check(ViewAssertions.matches(BackgroundViewMatcher(expectedRightSwipeBackground)))

        activityScenarioRule.scenario.onActivity {
            mItemView.prepareForSwipeDirection(SwipeDirection.LEFT)
        }

        Espresso.onView(ViewMatchers.isAssignableFrom(ChoosableItemViewProvider::class.java))
            .check(ViewAssertions.matches(BackgroundViewMatcher(expectedLeftSwipeBackground)))
    }
}