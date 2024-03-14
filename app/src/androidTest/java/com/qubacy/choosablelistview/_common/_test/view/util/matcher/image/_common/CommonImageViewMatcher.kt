package com.qubacy.choosablelistview._common._test.view.util.matcher.image._common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

class CommonImageViewMatcher : BaseMatcher<View> {
    private val mImage: Drawable

    constructor(image: Drawable) : super() {
        mImage = image
    }

    override fun describeTo(description: Description?) { }

    override fun matches(item: Any?): Boolean {
        if (item !is ImageView) return false

        item as ImageView

        val imageBitmap = mImage.toBitmap()
        val itemImageBitmap = item.drawable.toBitmap()

        return (itemImageBitmap.sameAs(imageBitmap))
    }
}