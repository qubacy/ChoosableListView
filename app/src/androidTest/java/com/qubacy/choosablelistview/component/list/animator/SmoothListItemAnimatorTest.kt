package com.qubacy.choosablelistview.component.list.animator

import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.view.util.action.wait.WaitViewAction
import com.qubacy.choosablelistview._common._test.view.util.matcher.position.TranslationViewMatcher
import com.qubacy.choosablelistview._common._test.view.util.matcher.scale.ScaleViewMatcher
import com.qubacy.choosablelistview.component.list.adapter.StringListAdapter
import com.qubacy.choosablelistview.component.list.item.content.StringContentItemView
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData
import com.qubacy.choosablelistviewlib.animator.SmoothListItemAnimator
import com.qubacy.choosablelistviewlib.item.ChoosableItemView
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmoothListItemAnimatorTest {
    companion object {
        val TEST_ITEM_DATA = StringContentItemData("test data")

        const val TEST_ANIMATION_DURATION = 300L
    }

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mItemTestData: StringContentItemData
    private lateinit var mViewHolder: StringListAdapter.StringListItemViewHolder
    private lateinit var mAnimator: SmoothListItemAnimator

    private var mAnimationDuration: Long = 0L

    @Before
    fun setup() {
        var activityContext: Context? = null

        activityScenarioRule.scenario.onActivity { activityContext = it }

        val itemView = createItemView(activityContext!!)

        activityScenarioRule.scenario.onActivity {
            it.setContentView(itemView)
        }

        mAnimationDuration = TEST_ANIMATION_DURATION
        mAnimator = createAnimator(mAnimationDuration)

        mItemTestData = TEST_ITEM_DATA
        mViewHolder = createViewHolder(itemView, mItemTestData)
    }

    private fun createItemView(
        context: Context
    ): ChoosableItemView<StringContentItemView, StringContentItemData> {
        val itemContentView = StringContentItemView(context, null)

        return ChoosableItemView(
            context = context,
            attrs = null,
            contentView = itemContentView
        )
    }

    private fun createViewHolder(
        itemView: ChoosableItemView<StringContentItemView, StringContentItemData>,
        itemData: StringContentItemData
    ): StringListAdapter.StringListItemViewHolder {
        val viewHolder = StringListAdapter.StringListItemViewHolder(itemView)

        viewHolder.setData(itemData)

        return viewHolder
    }

    private fun createAnimator(animationDuration: Long): SmoothListItemAnimator {
        return SmoothListItemAnimator(animationDuration)
    }

    @Test
    fun animateAddTest() {
        activityScenarioRule.scenario.onActivity {
            mAnimator.animateAdd(mViewHolder)
        }

        Espresso.onView(getItemMatcher())
            .check(ViewAssertions.matches(Matchers.not(isDisplayed())))

        waitForAnimationEnding()

        Espresso.onView(getItemMatcher())
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun animateRemoveTest() {
        activityScenarioRule.scenario.onActivity {
            mAnimator.animateRemove(mViewHolder)
        }

        Espresso.onView(getItemMatcher())
            .check(ViewAssertions.matches(
                Matchers.allOf(isDisplayed(), ScaleViewMatcher(scaleY = 1f))))

        waitForAnimationEnding()

        Espresso.onView(getItemMatcher())
            .check(ViewAssertions.matches(ScaleViewMatcher(scaleY = 0f)))
    }

    @Test
    fun animateMoveTest() {
        val fromTranslationX = 0
        val fromTranslationY = 0

        val toTranslationX = 0
        val toTranslationY = 100

        val expectedFromTranslationX = fromTranslationX
        val expectedFromTranslationY = -(toTranslationY - fromTranslationY)

        val expectedToTranslationX = toTranslationX
        val expectedToTranslationY = 0

        var gottenFromTranslationX = 0
        var gottenFromTranslationY = 0

        activityScenarioRule.scenario.onActivity {
            mAnimator.animateMove(
                mViewHolder,
                fromTranslationX, fromTranslationY,
                toTranslationX, toTranslationY
            )

            mViewHolder.itemView.also {
                gottenFromTranslationX = it.translationX.toInt()
                gottenFromTranslationY = it.translationY.toInt()
            }
        }

        Assert.assertEquals(expectedFromTranslationX, gottenFromTranslationX)
        Assert.assertEquals(expectedFromTranslationY, gottenFromTranslationY)

        waitForAnimationEnding()

        Espresso.onView(getItemMatcher())
            .check(ViewAssertions.matches(
                TranslationViewMatcher(expectedToTranslationX, expectedToTranslationY)))
    }

    private fun waitForAnimationEnding() {
        Espresso.onView(isRoot()).perform(WaitViewAction(mAnimationDuration))
    }

    private fun getItemMatcher(): Matcher<View> {
        return Matchers.allOf(
            hasDescendant(withText(mItemTestData.text)),
            isAssignableFrom(ChoosableItemView::class.java)
        )
    }
}