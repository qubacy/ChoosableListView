package com.qubacy.choosablelistviewlib.item.animator

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.utility.baserecyclerview.item.animator.BaseRecyclerViewItemAnimator

class ChoosableListItemAnimator(
    animationInterpolator: Interpolator = AccelerateDecelerateInterpolator()
) : BaseRecyclerViewItemAnimator() {
    companion object {
        const val TAG = "ChoosableListItemAnimator"
    }

    override val mInterpolator: Interpolator = animationInterpolator

//    override fun prepareHolderForRemoveAnimation(holder: RecyclerView.ViewHolder) {
//        holder.itemView.apply {
//            pivotX = (measuredWidth / 2).toFloat()
//            pivotY = 0f
//        }
//    }
//
//    override fun onAnimateRemoveEnded(view: View) { }
//
//    override fun prepareViewAnimatorForRemoveAnimation(animator: ViewPropertyAnimator) {
//        animator.scaleY(0f)
//    }

    override fun prepareHolderForAddAnimation(holder: RecyclerView.ViewHolder) {
        holder.itemView.apply {
            translationX = holder.itemView.measuredWidth.toFloat()
            alpha = 0f
        }
    }

    override fun onAnimateAddCancelled(view: View) {
        view.apply {
            translationX = 0f
            alpha = 1f
        }
    }

    override fun prepareViewAnimatorForAddAnimation(animator: ViewPropertyAnimator) {
        animator.apply {
            translationX(0f)
            alpha(1f)
        }
    }
}