package com.qubacy.choosablelistview._common._test.view.util.matcher.background

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class BackgroundViewMatcher(
    val backgroundDrawable: Drawable
) : BaseMatcher<View>() {
    companion object {
        const val DEFAULT_BITMAP_SIZE = 48
    }

    override fun describeTo(description: Description?) { }

    override fun matches(item: Any?): Boolean {
        if (item == null) return false
        if (item !is View) return false

        val expectedDrawableBitmap = backgroundDrawable
            .toBitmap(DEFAULT_BITMAP_SIZE, DEFAULT_BITMAP_SIZE)
        val itemDrawableBitmap = item.background
            .toBitmap(DEFAULT_BITMAP_SIZE, DEFAULT_BITMAP_SIZE)

        return (itemDrawableBitmap.sameAs(expectedDrawableBitmap))
    }
}