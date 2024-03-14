package com.qubacy.choosablelistview._common._test.activity.scenario.util

import android.content.Context
import androidx.test.core.app.ActivityScenario

fun ActivityScenario<*>.getContext(): Context {
    var activityContext: Context? = null

    onActivity { activityContext = it }

    return activityContext!!
}