package com.qubacy.choosablelistview._common._test.view.util.action.recyclerview.scroll

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher

class ScrollRecyclerViewViewAction(
    val position: Int
) : ViewAction {
    override fun getDescription(): String = String()

    override fun getConstraints(): Matcher<View> {
        return allOf(
            isAssignableFrom(RecyclerView::class.java),
            isDisplayed()
        )
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView

        recyclerView.scrollToPosition(position)

        uiController?.loopMainThreadUntilIdle()
    }
}