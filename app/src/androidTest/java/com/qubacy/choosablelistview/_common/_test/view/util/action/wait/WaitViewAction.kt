package com.qubacy.choosablelistview._common._test.view.util.action.wait

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class WaitViewAction(
    private val mDuration: Long
) : ViewAction {
    override fun getDescription(): String = "Waiting action!"

    override fun getConstraints(): Matcher<View> = Matchers.any(View::class.java)

    override fun perform(uiController: UiController?, view: View?) {
        if (uiController == null) return

        val endTime = System.currentTimeMillis() + mDuration

        uiController.loopMainThreadUntilIdle()

        while (System.currentTimeMillis() < endTime) {
            uiController.loopMainThreadForAtLeast(100)
        }
    }
}