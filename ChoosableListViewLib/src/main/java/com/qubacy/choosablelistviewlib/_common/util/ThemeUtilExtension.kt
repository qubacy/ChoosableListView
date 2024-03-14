package com.qubacy.choosablelistviewlib._common.util

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

fun Theme.resolveIntegerAttr(@AttrRes integerAttr: Int): Int {
    val typedValue = TypedValue()

    if (!resolveAttribute(integerAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.data
}

fun Theme.resolveFractionAttr(@AttrRes fractionAttr: Int): Float {
    val typedValue = TypedValue()

    if (!resolveAttribute(fractionAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.float
}

fun Theme.resolveDimenAttr(@AttrRes dimenAttr: Int): Float {
    val typedValue = TypedValue()

    if (!resolveAttribute(dimenAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.getDimension(resources.displayMetrics)
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
