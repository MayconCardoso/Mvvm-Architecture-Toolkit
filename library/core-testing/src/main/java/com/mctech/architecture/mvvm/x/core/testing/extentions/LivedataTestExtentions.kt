package com.mctech.architecture.mvvm.x.core.testing.extentions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.runBlocking

fun <T> LiveData<T>.testLiveData(
    scenario    : suspend () -> Unit = {},
    action      : suspend () -> Unit = {},
    assertion   : suspend (List<T>) -> Unit
) {
    val emittedValues = mutableListOf<T>()
    val observer = Observer<T> {
        emittedValues.add(it)
    }

    try {
        runBlocking {
            scenario()
            observeForever(observer)
            action()
            assertion(emittedValues)
        }
    } finally {
        removeObserver(observer)
    }
}

fun <T> LiveData<T>.assertIsEmpty() {
    testLiveData { it.assertEmpty() }
}