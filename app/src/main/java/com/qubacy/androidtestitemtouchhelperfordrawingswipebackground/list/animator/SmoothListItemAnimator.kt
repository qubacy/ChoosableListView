package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.list.animator

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class SmoothListItemAnimator(
    val animationDuration: Long,
    val animationInterpolator: Interpolator = AccelerateDecelerateInterpolator()
) : SimpleItemAnimator() {
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        return false
    }

    override fun runPendingAnimations() {

    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {

    }

    override fun endAnimations() {

    }

    override fun isRunning(): Boolean {
        return false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder == null) return false

        val onStartAction = {
            holder.itemView.apply {
                pivotX = (measuredWidth / 2).toFloat()
                pivotY = 0f
            }

            Unit
        }
        val onEndAction = {
            dispatchRemoveFinished(holder)

            holder.itemView.scaleY = 1f
        }

        holder.itemView.animate().apply {
            scaleY(0f)

            duration = animationDuration
            interpolator = animationInterpolator
        }.setListener(createAnimatorListener(onStartAction, onEndAction)).start()

        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder == null) return false

        holder.itemView.apply {
            translationX = holder.itemView.measuredWidth.toFloat()
            alpha = 0f
        }

        val onEndAction = { dispatchAddFinished(holder) }

        holder.itemView.animate().apply {
            translationX(0f)
            alpha(1f)

            duration = animationDuration
            interpolator = animationInterpolator
        }.setListener(createAnimatorListener({}, onEndAction)).start()

        return true
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        if (holder == null) return false

        val deltaY = toY - fromY

        holder.itemView.apply {
            translationY = (-deltaY).toFloat()
        }

        val startAction = { dispatchMoveStarting(holder) }
        val endAction = { dispatchMoveFinished(holder) }

        holder.itemView.animate().apply {
            translationY(0f)

            duration = animationDuration
            interpolator = animationInterpolator
        }.setListener(createAnimatorListener(startAction, endAction)).start()

        return true
    }

    private fun createAnimatorListener(
        startAction: () -> Unit,
        endAction: () -> Unit
    ): AnimatorListener {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) { startAction() }
            override fun onAnimationCancel(animation: Animator) { endAction() }
            override fun onAnimationEnd(animation: Animator) { endAction() }
        }
    }
}