package com.qubacy.choosablelistview._common._test.view.util.action.swipe

import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe

object SwipeViewActionUtil {
    fun generateSwipeViewAction(
        endX: Float,
        endY: Float,
        startCoordsProvider: CoordinatesProvider = GeneralLocation.CENTER
    ): ViewAction {
        return GeneralSwipeAction(
            Swipe.SLOW, startCoordsProvider, { floatArrayOf(endX, endY) }, Press.FINGER
        )
    }
}