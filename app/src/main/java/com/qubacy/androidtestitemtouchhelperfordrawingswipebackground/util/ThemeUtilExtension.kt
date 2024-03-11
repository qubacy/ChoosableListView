package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.util

import android.content.res.Resources.Theme
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Theme.resolveColorAttr(@AttrRes colorAttr: Int): Int {
    val typedValue = TypedValue()

    if (!resolveAttribute(colorAttr, typedValue, true))
        throw IllegalArgumentException()

    return typedValue.data
}