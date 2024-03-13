package com.qubacy.choosablelistview._common._test.view.util.matcher.scale

import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class ScaleViewMatcher(
    val scaleX: Float? = null,
    val scaleY: Float? = null
) : BaseMatcher<View>() {
    init {
        if (scaleX == null && scaleY == null)
            throw IllegalArgumentException()
    }

    override fun describeTo(description: Description?) { }

    override fun matches(item: Any?): Boolean {
        if (item == null) return false
        if (item !is View) return false

        scaleX?.also { if (item.scaleX != it) return false }
        scaleY?.also { if (item.scaleY != it) return false }

        return true
    }
}