package com.qubacy.choosablelistview._common._test.view.util.matcher.position

import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class TranslationViewMatcher(
    val translationX: Int = 0,
    val translationY: Int = 0
) : BaseMatcher<View>() {
    override fun describeTo(description: Description?) { }

    override fun matches(item: Any?): Boolean {
        if (item == null) return false
        if (item !is View) return false

        return (item.translationX == translationX.toFloat()
                && item.translationY == translationY.toFloat())
    }
}