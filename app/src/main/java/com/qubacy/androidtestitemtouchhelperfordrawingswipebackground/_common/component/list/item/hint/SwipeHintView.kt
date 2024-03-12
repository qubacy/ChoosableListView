package com.qubacy.androidtestitemtouchhelperfordrawingswipebackground._common.component.list.item.hint

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
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.R
import com.qubacy.androidtestitemtouchhelperfordrawingswipebackground.databinding.ComponentListItemBackgroundHintBinding

class SwipeHintView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    companion object {
        const val TAG = "SwipeHintView"
    }

    private var mBinding: ComponentListItemBackgroundHintBinding? = null

    private lateinit var mAnimatedImage: AnimatedVectorDrawableCompat
    private var mSwipeHintAnimated: Boolean = false

    @DrawableRes
    private var mImageResId: Int = 0
    @ColorInt
    private var mImageColor: Int = 0
    private lateinit var mText: String
    @ColorInt
    private var mTextColor: Int = 0

    init {
        initAttrs(attrs)
        initLayoutParams()
    }

    fun isInitialized(): Boolean {
        return mBinding != null
    }

    fun setContent(
        @DrawableRes imageResId: Int = mImageResId,
        @ColorInt imageColor: Int = mImageColor,
        text: String = mText,
        @ColorInt textColor: Int = mTextColor
    ) {
        mImageResId = imageResId
        mImageColor = imageColor
        mText = text
        mTextColor = textColor

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

        mBinding = ComponentListItemBackgroundHintBinding
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
                R.attr.swipeHintViewTextColor
            )
        ).apply {
            try {
                mImageResId = getResourceId(0, 0)
                mImageColor = getColor(1, 0)
                mText = getString(2) ?: String()
                mTextColor = getColor(3, 0)

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
        mAnimatedImage = AnimatedVectorDrawableCompat.create(context, mImageResId)!!.apply {
            val wrappedImage = DrawableCompat.wrap(this)

            DrawableCompat.setTint(wrappedImage, mImageColor)
        }

        mBinding!!.apply {
            alpha = 0f

            componentListItemBackgroundHintImage.setImageDrawable(mAnimatedImage)
            componentListItemBackgroundHintText.setTextColor(mTextColor)
            componentListItemBackgroundHintText.text = mText
        }

        mBinding!!.componentListItemBackgroundHintImage.setImageDrawable(mAnimatedImage)
        mBinding!!.componentListItemBackgroundHintText.setTextColor(mTextColor)
        mBinding!!.componentListItemBackgroundHintText.text = mText
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