package com.qubacy.choosablelistview.component.list.adapter

import android.view.ViewGroup
import com.qubacy.choosablelistview.component.list.adapter._common._test.util.mock.AnyMockUtil
import com.qubacy.choosablelistview.component.list.adapter.producer.StringItemViewProducer
import com.qubacy.choosablelistview.component.list.item.content.data.StringContentItemData
import com.qubacy.choosablelistviewlib.adapter.ChoosableListAdapter
import com.qubacy.choosablelistviewlib.item.ChoosableItemView
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class StringListAdapterTest {
    private lateinit var mAdapter: StringListAdapter
    private lateinit var mViewHolder: StringListAdapter.StringListItemViewHolder
    private lateinit var mItems: MutableList<StringContentItemData>

    private val mCreateItemViewCallFlag: AtomicBoolean = AtomicBoolean(false)
    private val mCreateViewHolderCallFlag: AtomicBoolean = AtomicBoolean(false)
    private val mViewHolderSetData: AtomicReference<StringContentItemData?> =
        AtomicReference(null)

    private val mNotifyItemInsertedCallPosition: AtomicReference<Int?> =
        AtomicReference(null)
    private val mNotifyItemRemovedCallPosition: AtomicReference<Int?> = AtomicReference(null)
    private val mNotifyDataSetChangedCallFlag: AtomicBoolean = AtomicBoolean(false)

    @Before
    fun setup() {
        initViewHolder()
        initItems()
        initAdapter()
    }

    @After
    fun clear() {
        mCreateItemViewCallFlag.set(false)
        mCreateViewHolderCallFlag.set(false)
        mViewHolderSetData.set(null)
        mNotifyItemRemovedCallPosition.set(null)
        mNotifyItemInsertedCallPosition.set(null)
        mNotifyDataSetChangedCallFlag.set(false)
    }

    private fun initViewHolder() {
        val viewHolderMock = Mockito.mock(StringListAdapter.StringListItemViewHolder::class.java)

        Mockito.`when`(viewHolderMock.setData(AnyMockUtil.anyObject()))
            .thenAnswer {
                val data = it.arguments[0] as StringContentItemData

                mViewHolderSetData.set(data)
            }

        mViewHolder = viewHolderMock
    }

    private fun initItems() {
        mItems = mutableListOf()
    }

    private fun initAdapter() {
        val itemViewProducer = Mockito.mock(StringItemViewProducer::class.java)

        Mockito.`when`(itemViewProducer.createItemView(AnyMockUtil.anyObject(), Mockito.anyInt()))
            .thenAnswer {
                mCreateItemViewCallFlag.set(true)

                Mockito.mock(ChoosableItemView::class.java)
            }

        val adapterSpy = Mockito.spy(StringListAdapter(itemViewProducer))

        ChoosableListAdapter::class.java
            .getDeclaredField("mItems")
            .apply { isAccessible = true }
            .set(adapterSpy, mItems)

        Mockito.doReturn(itemViewProducer).`when`(adapterSpy).itemViewProducer
        Mockito.doAnswer{
            mCreateViewHolderCallFlag.set(true)

            Mockito.mock(StringListAdapter.StringListItemViewHolder::class.java)

        }.`when`(adapterSpy).createViewHolder(AnyMockUtil.anyObject())

        Mockito.doAnswer {
            val position = it.arguments[0] as Int

            mNotifyItemRemovedCallPosition.set(position)
        }.`when`(adapterSpy).wrappedNotifyItemRemoved(Mockito.anyInt())
        Mockito.doAnswer {
            val position = it.arguments[0] as Int

            mNotifyItemInsertedCallPosition.set(position)
        }.`when`(adapterSpy).wrappedNotifyItemInserted(Mockito.anyInt())
        Mockito.doAnswer {
            mNotifyDataSetChangedCallFlag.set(true)
        }.`when`(adapterSpy).wrappedNotifyDataSetChanged()

        mAdapter = adapterSpy
    }

    @Test
    fun onCreateViewHolderTest() {
        val parentMock = Mockito.mock(ViewGroup::class.java)
        val viewType = 0

        mAdapter.onCreateViewHolder(parentMock, viewType)

        Assert.assertTrue(mCreateViewHolderCallFlag.get())
        Assert.assertTrue(mCreateItemViewCallFlag.get())
    }

    @Test
    fun onBindViewHolderTest() {
        val position = 0
        val expectedData = StringContentItemData("test data")

        mItems.add(position, expectedData)

        mAdapter.onBindViewHolder(mViewHolder, position)

        Assert.assertEquals(expectedData, mViewHolderSetData.get())
    }

    @Test
    fun getItemCountTest() {
        val expectedStartItemCount = 0

        mItems.clear()

        Assert.assertEquals(expectedStartItemCount, mAdapter.itemCount)

        val expectedEndItemCount = 2

        setGeneratedItems(expectedEndItemCount)

        Assert.assertEquals(expectedEndItemCount, mAdapter.itemCount)
    }

    @Test
    fun removeItemAtPositionTest() {
        val initItemCount = 2

        setGeneratedItems(initItemCount)

        val expectedRemovedItemPosition = 1
        val expectedItems = mItems.toMutableList().apply {
            removeAt(expectedRemovedItemPosition)
        }

        mAdapter.removeItemAtPosition(expectedRemovedItemPosition)

        val gottenItems = mItems

        Assert.assertEquals(expectedRemovedItemPosition, mNotifyItemRemovedCallPosition.get())

        Assert.assertEquals(expectedItems.size, gottenItems.size)

        for (expectedItem in expectedItems)
            Assert.assertTrue(gottenItems.contains(expectedItem))
    }

    @Test
    fun addItemTest() {
        val initItemCount = 2

        setGeneratedItems(initItemCount)

        val expectedAddedItem = StringContentItemData("added item")
        val expectedAddedItemPosition = mItems.size
        val expectedItems = mItems.toMutableList().apply { add(expectedAddedItem) }

        mAdapter.addItem(expectedAddedItem)

        val gottenItems = mItems

        Assert.assertEquals(expectedAddedItemPosition, mNotifyItemInsertedCallPosition.get())

        Assert.assertEquals(expectedItems.size, gottenItems.size)

        for (expectedItem in expectedItems)
            Assert.assertTrue(gottenItems.contains(expectedItem))

        Assert.assertEquals(expectedAddedItem, mItems.last())
    }

    @Test
    fun setItemsTest() {
        val initItemCount = 2

        setGeneratedItems(initItemCount)

        val expectedItemCount = 3
        val expectedItems = generateItems(expectedItemCount, initItemCount)

        mAdapter.setItems(expectedItems)

        val gottenItems = mItems

        Assert.assertTrue(mNotifyDataSetChangedCallFlag.get())

        Assert.assertEquals(expectedItems.size, gottenItems.size)

        for (expectedItem in expectedItems)
            Assert.assertTrue(gottenItems.contains(expectedItem))
    }

    private fun setGeneratedItems(count: Int, postfixOffset: Int = 0) {
        val items = generateItems(count, postfixOffset)

        mItems.apply {
            clear()
            addAll(items)
        }
    }

    private fun generateItems(count: Int, postfixOffset: Int = 0): List<StringContentItemData> {
        val items = mutableListOf<StringContentItemData>()

        for (i in 0 until count)
            items.add(StringContentItemData("item ${i + postfixOffset}"))

        return items
    }
}