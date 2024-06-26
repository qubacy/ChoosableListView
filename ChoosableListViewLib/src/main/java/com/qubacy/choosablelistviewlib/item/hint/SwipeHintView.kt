package com.qubacy.choosablelistviewlib.item.hint

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.updateLayoutParams
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.qubacy.choosablelistviewlib.R
import com.qubacy.choosablelistviewlib.databinding.ComponentChoosableListItemHintBinding

class SwipeHintView(
    context: Context,
    attrs: AttributeSet?
) : LinearLayout(context, attrs) {
    companion object {
        const val TAG = "SwipeHintView"
    }

    private var mBinding: ComponentChoosableListItemHintBinding? = null

    private lateinit var mAnimatedImage: AnimatedVectorDrawableCompat
    private var mSwipeHintAnimated: Boolean = false

    @DrawableRes
    private var mIconResId: Int = 0
    @ColorInt
    private var mIconTint: Int = 0
    private lateinit var mText: String
    @ColorInt
    private var mTextColor: Int = 0
    private var mIconSize: Int = 0

    init {
        if (attrs != null) initAttrs(attrs)

        initLayoutParams()
    }

    fun isInitialized(): Boolean {
        return mBinding != null
    }

    fun setContent(
        @DrawableRes iconResId: Int = mIconResId,
        @ColorInt iconTint: Int = mIconTint,
        text: String = mText,
        @ColorInt textColor: Int = mTextColor,
        iconSize: Int = mIconSize
    ) {
        mIconResId = iconResId
        mIconTint = iconTint
        mText = text
        mTextColor = textColor
        mIconSize = iconSize

        if (mBinding != null) initContent()
    }

    fun init(): SwipeHintView {
        if (mBinding != null) throw IllegalStateException()

        inflate()
        initContent()

        return this
    }

    private fun inflate() {
        val layoutInflater = LayoutInflater.from(context)

        mBinding = ComponentChoosableListItemHintBinding
            .inflate(layoutInflater, this)
    }

    @SuppressLint("ResourceType")
    private fun initAttrs(attrs: AttributeSet) {
        context.obtainStyledAttributes(
            attrs,
            intArrayOf(
                R.attr.swipeHintViewImage,
                R.attr.swipeHintViewImageColor,
                R.attr.swipeHintViewText,
                R.attr.swipeHintViewTextColor,
                R.attr.swipeHintViewIconSize
            )
        ).apply {
            try {
                mIconResId = getResourceId(0, 0)
                mIconTint = getColor(1, 0)
                mText = getString(2) ?: String()
                mTextColor = getColor(3, 0)
                mIconSize = getDimension(4, 0f).toInt()

            } finally {
                recycle()
            }
        }
    }

    private fun initLayoutParams() {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        orientation = VERTICAL
    }

    private fun initContent() {
        mAnimatedImage = AnimatedVectorDrawableCompat.create(context, mIconResId)!!.apply {
            val wrappedImage = DrawableCompat.wrap(this)

            DrawableCompat.setTint(wrappedImage, mIconTint)
        }

        mBinding!!.apply {
            alpha = 0f

            componentListItemHintIcon.setImageDrawable(mAnimatedImage)
            componentListItemHintIcon.updateLayoutParams<LayoutParams> {
                width = mIconSize
                height = mIconSize
            }
            componentListItemHintText.setTextColor(mTextColor)
            componentListItemHintText.text = mText
        }
    }

    fun resetAnimation() {
        mSwipeHintAnimated = false

        mBinding!!.root.alpha = 0f
    }

    fun animateWithProgress(
        @FloatRange(0.0, 1.0) progress: Float
    ) {
        alpha = progress

        if (!mSwipeHintAnimated) {
            mSwipeHintAnimated = true

            mAnimatedImage.start()
        }
    }
}