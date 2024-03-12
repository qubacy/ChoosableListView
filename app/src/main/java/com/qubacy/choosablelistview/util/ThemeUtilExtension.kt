package com.qubacy.choosablelistview.util

import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

@ColorInt
fun Theme.resolveColorAttr(@AttrRes colorAttr: Int): Int {
    val typedValue = TypedValue()

    if (!resolveAttribute(colorAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.data
}

fun Theme.resolveStringAttr(@AttrRes stringAttr: Int): String {
    val typedValue = TypedValue()

    if (!resolveAttribute(stringAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.string.toString()
}

@DrawableRes
fun Theme.resolveDrawableAttr(@AttrRes drawableAttr: Int): Int {
    val typedValue = TypedValue()

    if (!resolveAttribute(drawableAttr, typedValue, false))
        throw IllegalArgumentException()

    return typedValue.data
}
