package com.qubacy.choosablelistview.component.list.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.qubacy.choosablelistview.component.list.adapter._common._test.util.mock.AnyMockUtil
import com.qubacy.choosablelistview.component.list.item.content.StringContentItemView
import com.qubacy.choosablelistviewlib._common.direction.SwipeDirection
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter
import com.qubacy.choosablelistviewlib.helper.ChoosableListItemTouchHelperCallback
import com.qubacy.choosablelistviewlib.item.ChoosableItemView
import com.qubacy.choosablelistviewlib.item.hint.SwipeHintView
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ChoosableListItemTouchHelperCallbackTest {
    companion object {
        const val DEFAULT_ITEM_VIEW_WIDTH = 100
    }

    private lateinit var mCanvas: Canvas
    private var mItemViewWidth: Int = DEFAULT_ITEM_VIEW_WIDTH
    private lateinit var mHelperCallback: ChoosableListItemTouchHelperCallback.Callback
    private lateinit var mCallback: ChoosableListItemTouchHelperCallback
    private lateinit var mViewHolder: ChoosableListAdapter.ChoosableListItemViewHolder<*, *>

    private var mCanvasDrawBitmapCallFlag: Boolean = false

    private var mOnItemSwipedDirection: SwipeDirection? = null

    private var mItemViewResetViewCallFlag: Boolean = false
    private var mItemViewPrepareForSwipeDirection: SwipeDirection? = null

    private var mLeftSwipingHintInitCallFlag: Boolean = false
    private var mLeftSwipingHintAnimateWithProgressCallFlag: Boolean = false
    private var mLeftSwipingHintResetAnimationCallFlag: Boolean = false

    private var mRightSwipingHintInitCallFlag: Boolean = false
    private var mRightSwipingHintAnimateWithProgressCallFlag: Boolean = false
    private var mRightSwipingHintResetAnimationCallFlag: Boolean = false

    @Before
    fun setup() {
        initHelperCallback()
        initCallback()
        iniViewHolder()
    }

    @After
    fun clear() {
        mCanvasDrawBitmapCallFlag = false

        mOnItemSwipedDirection = null

        mItemViewResetViewCallFlag = false
        mItemViewPrepareForSwipeDirection = null

        mLeftSwipingHintInitCallFlag = false
        mLeftSwipingHintAnimateWithProgressCallFlag = false
        mLeftSwipingHintResetAnimationCallFlag = false

        mRightSwipingHintInitCallFlag = false
        mRightSwipingHintAnimateWithProgressCallFlag = false
        mRightSwipingHintResetAnimationCallFlag = false
    }

    private fun initHelperCallback() {
        mHelperCallback = object : ChoosableListItemTouchHelperCallback.Callback {
            override fun onItemSwiped(direction: SwipeDirection, position: Int) {
                mOnItemSwipedDirection = direction
            }
        }
    }

    private fun initCallback() {
        val canvas = Mockito.mock(Canvas::class.java)

        Mockito.`when`(canvas.drawBitmap(
            AnyMockUtil.anyObject(), Mockito.anyFloat(), Mockito.anyFloat(), AnyMockUtil.anyObject()
        )).thenAnswer {
            mCanvasDrawBitmapCallFlag = true

            Unit
        }

        mCanvas = canvas

        val bitmapMock = Mockito.mock(Bitmap::class.java)
        val rectMock = Mockito.mock(Rect::class.java)
        val canvasMock = Mockito.mock(Canvas::class.java)

        val callback = ChoosableListItemTouchHelperCallback(mCallback = mHelperCallback)
        val callbackSpy = Mockito.spy(callback)

        Mockito.doReturn(bitmapMock)
            .`when`(callbackSpy).createItemContentBitmap(AnyMockUtil.anyObject())
        Mockito.doReturn(rectMock)
            .`when`(callbackSpy).createItemViewRect(AnyMockUtil.anyObject())
        Mockito.doReturn(canvasMock)
            .`when`(callbackSpy).createItemContentCanvas(AnyMockUtil.anyObject())

        mCallback = callbackSpy
    }

    private fun iniViewHolder() {
        val leftSwipingHintView = Mockito.mock(SwipeHintView::class.java)

        Mockito.`when`(leftSwipingHintView.init()).thenAnswer {
            mLeftSwipingHintInitCallFlag = true

            leftSwipingHintView
        }
        Mockito.`when`(leftSwipingHintView.isInitialized()).thenReturn(false)
        Mockito.`when`(leftSwipingHintView.animateWithProgress(Mockito.anyFloat()))
            .thenAnswer {
                mLeftSwipingHintAnimateWithProgressCallFlag = true

                Unit
            }
        Mockito.`when`(leftSwipingHintView.resetAnimation()).thenAnswer {
            mLeftSwipingHintResetAnimationCallFlag = true

            Unit
        }

        val rightSwipingHintView = Mockito.mock(SwipeHintView::class.java)

        Mockito.`when`(rightSwipingHintView.init()).thenAnswer {
            mRightSwipingHintInitCallFlag = true

            rightSwipingHintView
        }
        Mockito.`when`(rightSwipingHintView.isInitialized()).thenReturn(false)
        Mockito.`when`(rightSwipingHintView.animateWithProgress(Mockito.anyFloat()))
            .thenAnswer {
                mRightSwipingHintAnimateWithProgressCallFlag = true

                Unit
            }
        Mockito.`when`(rightSwipingHintView.resetAnimation()).thenAnswer {
            mRightSwipingHintResetAnimationCallFlag = true

            Unit
        }

        // todo: how to mock with a base class and an interface?:
        val itemContentViewMock = Mockito.mock(StringContentItemView::class.java)

        Mockito.`when`(itemContentViewMock.draw(AnyMockUtil.anyObject()))
            .thenAnswer {  }

        val itemViewMock = Mockito.mock(ChoosableItemView::class.java)

        Mockito.`when`(itemViewMock.contentView).thenReturn(itemContentViewMock)
        Mockito.`when`(itemViewMock.measuredWidth).thenAnswer { mItemViewWidth }
        Mockito.`when`(itemViewMock.getLeftSwipingHintView()).thenReturn(leftSwipingHintView)
        Mockito.`when`(itemViewMock.getRightSwipingHintView()).thenReturn(rightSwipingHintView)
        Mockito.`when`(itemViewMock.resetView()).thenAnswer {
            mItemViewResetViewCallFlag = true

            Unit
        }
        Mockito.`when`(itemViewMock.prepareForSwipeDirection(AnyMockUtil.anyObject()))
            .thenAnswer {
                val swipeDirection = it.arguments[0] as SwipeDirection

                mItemViewPrepareForSwipeDirection = swipeDirection

                Unit
            }
        Mockito.`when`(itemViewMock.setTranslationX(Mockito.anyFloat())).thenAnswer {  }
        Mockito.`when`(itemViewMock.setTranslationY(Mockito.anyFloat())).thenAnswer {  }

        val viewHolderMock = Mockito.mock(
            ChoosableListAdapter.ChoosableListItemViewHolder::class.java
        )

        Mockito.`when`(viewHolderMock.choosableItemView).thenReturn(itemViewMock)
        Mockito.`when`(viewHolderMock.adapterPosition).thenReturn(0)

        mViewHolder = viewHolderMock
    }

    @Test
    fun onChildDrawWithNullDeltaTest() {
        val deltaX = 0f
        val deltaY = 0f

        runOnChildDraw(deltaX, deltaY)

        Assert.assertFalse(mLeftSwipingHintInitCallFlag)
        Assert.assertFalse(mRightSwipingHintInitCallFlag)
        Assert.assertTrue(mCanvasDrawBitmapCallFlag)
    }

    @Test
    fun onChildDrawWithPositiveDeltaTest() {
        val deltaX = mItemViewWidth.toFloat()
        val deltaY = 0f

        val expectedSwipeDirection = SwipeDirection.RIGHT

        runOnChildDraw(deltaX, deltaY)

        Assert.assertFalse(mLeftSwipingHintInitCallFlag)
        Assert.assertTrue(mRightSwipingHintInitCallFlag)

        Assert.assertTrue(mRightSwipingHintAnimateWithProgressCallFlag)
        Assert.assertEquals(expectedSwipeDirection, mItemViewPrepareForSwipeDirection)

        Assert.assertTrue(mCanvasDrawBitmapCallFlag)
    }

    @Test
    fun onChildDrawWithNegativeDeltaTest() {
        val deltaX = (-mItemViewWidth).toFloat()
        val deltaY = 0f

        val expectedSwipeDirection = SwipeDirection.LEFT

        runOnChildDraw(deltaX, deltaY)

        Assert.assertFalse(mRightSwipingHintInitCallFlag)
        Assert.assertTrue(mLeftSwipingHintInitCallFlag)

        Assert.assertTrue(mLeftSwipingHintAnimateWithProgressCallFlag)
        Assert.assertEquals(expectedSwipeDirection, mItemViewPrepareForSwipeDirection)

        Assert.assertTrue(mCanvasDrawBitmapCallFlag)
    }

    @Test
    fun onSwipedRightTest() {
        val rightSwipeDirection = ItemTouchHelper.RIGHT
        val expectedSwipeDirection = SwipeDirection.RIGHT

        mCallback.onSwiped(mViewHolder, rightSwipeDirection)

        Assert.assertEquals(expectedSwipeDirection, mOnItemSwipedDirection)
    }

    @Test
    fun onSwipedLeftTest() {
        val leftSwipeDirection = ItemTouchHelper.LEFT
        val expectedSwipeDirection = SwipeDirection.LEFT

        mCallback.onSwiped(mViewHolder, leftSwipeDirection)

        Assert.assertEquals(expectedSwipeDirection, mOnItemSwipedDirection)
    }

    @Deprecated("Can't be implemented because it's impossible to mock ViewHolder fully (itemView is final);")
    @Test
    fun clearViewTest() {
        val recyclerViewMock = Mockito.mock(RecyclerView::class.java)

        mCallback.clearView(recyclerViewMock, mViewHolder)

        Assert.assertTrue(mItemViewResetViewCallFlag)
    }

    private fun runOnChildDraw(dX: Float, dY: Float) {
        val recyclerViewMock = Mockito.mock(RecyclerView::class.java)

        mCallback.onChildDraw(
            mCanvas, recyclerViewMock, mViewHolder,
            dX, dY,
            0, true
        )
    }
}