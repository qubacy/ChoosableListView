package com.qubacy.choosablelistview.component.list.item.hint

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qubacy.choosablelistview.MainActivity
import com.qubacy.choosablelistview._common._test.activity.scenario.util.getContext
import com.qubacy.choosablelistview._common._test.view.util.action.wait.WaitViewAction
import com.qubacy.choosablelistview._common._test.view.util.matcher.image._common.CommonImageViewMatcher
import com.qubacy.choosablelistview._common._test.view.util.matcher.image.animated.AnimatedImageViewMatcher
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView
import com.qubacy.choosablelistviewlib.R
import com.qubacy.choosablelistviewlib._common.util.resolveIntegerAttr
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeHintViewTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @DrawableRes
    private var mInitImageRes: Int = 0
    private var mInitText: String = String()
    private var mInitIconSize: Int = 0

    private lateinit var mSwipeHintView: SwipeHintView

    @Before
    fun setup() {
        mInitImageRes = R.drawable.ic_checkmark_animated
        mInitText = "test text"
        mInitIconSize = 48

        initSwipeHintView()
        initContent()
    }

    private fun initSwipeHintView() {
        activityScenarioRule.scenario.onActivity {
            mSwipeHintView = SwipeHintView(it, null)

            it.setContentView(mSwipeHintView)
        }
    }

    private fun initContent() {
        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.setContent(
                mInitImageRes,
                com.qubacy.choosablelistview.R.color.black,
                mInitText,
                com.qubacy.choosablelistview.R.color.black,
                mInitIconSize
            )
        }
    }

    @Test
    fun initTest() {
        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.doesNotExist())
        Espresso.onView(withId(R.id.component_list_item_hint_text))
            .check(ViewAssertions.doesNotExist())

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.init()
        }

        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.component_list_item_hint_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun setContentTest() {
        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.init()
        }

        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.matches(
                AnimatedImageViewMatcher(
                    activityScenarioRule.scenario.getContext(),
                    mInitImageRes
                )
            ))
        Espresso.onView(withId(R.id.component_list_item_hint_text))
            .check(ViewAssertions.matches(ViewMatchers.withText(mInitText)))

        val expectedIconResId = R.drawable.ic_cross_animated
        val expectedText = "new text"

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.setContent(iconResId = expectedIconResId, text = expectedText)
        }

        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.matches(
                AnimatedImageViewMatcher(
                    activityScenarioRule.scenario.getContext(),
                    expectedIconResId
                )
            ))
        Espresso.onView(withId(R.id.component_list_item_hint_text))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
    }

    @Test
    fun animateWithProgressTest() {
        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.init()
        }

        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.matches(
                AnimatedImageViewMatcher(
                    activityScenarioRule.scenario.getContext(),
                    R.drawable.ic_checkmark_animated
                )))

        val animationDuration = activityScenarioRule.scenario.getContext().theme
            .resolveIntegerAttr(R.attr.choosableListItemHintIconAnimationDuration).toLong()
        val startProgress = 0f
        val expectedStartAlpha = 0f

        var expectedImage: Drawable? = null

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.animateWithProgress(startProgress)

            expectedImage = it.resources.getDrawable(R.drawable.ic_checkmark, it.theme)
        }

        Espresso.onView(isAssignableFrom(SwipeHintView::class.java))
            .perform(WaitViewAction(animationDuration))
            .check(ViewAssertions.matches(ViewMatchers.withAlpha(expectedStartAlpha)))
        Espresso.onView(withId(R.id.component_list_item_hint_icon))
            .check(ViewAssertions.matches(
                Matchers.not(CommonImageViewMatcher(
                    expectedImage!!
                ))))

        val endProgress = 1f
        val expectedEndAlpha = 1f

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.animateWithProgress(endProgress)
        }

        Espresso.onView(isAssignableFrom(SwipeHintView::class.java))
            .perform(WaitViewAction(animationDuration))
            .check(ViewAssertions.matches(ViewMatchers.withAlpha(expectedEndAlpha)))
    }

    @Test
    fun resetAnimationTest() {
        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.init()
        }

        val startProgress = 1f
        val startAlpha = 1f
        val expectedAlpha = 0f

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.animateWithProgress(startProgress)
        }

        Espresso.onView(isAssignableFrom(SwipeHintView::class.java))
            .check(ViewAssertions.matches(ViewMatchers.withAlpha(startAlpha)))

        activityScenarioRule.scenario.onActivity {
            mSwipeHintView.resetAnimation()
        }

        Espresso.onView(isAssignableFrom(SwipeHintView::class.java))
            .check(ViewAssertions.matches(ViewMatchers.withAlpha(expectedAlpha)))
    }
}