package com.qubacy.choosablelistview.component.list.adapter._common._test.util.mock

import org.mockito.Mockito

object AnyMockUtil {
    fun <T> anyObject(): T {
        Mockito.any<T>()

        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}