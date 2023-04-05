package io.github.mayconcardoso.mvvm.core.testing.agent

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal class LiveDataObserverAgent<T>(
  private val liveData: LiveData<T>,
  private val assert: suspend (List<T>) -> Unit,
) {
  private val emittedValues = mutableListOf<T>()
  private val observer = Observer<T> {
    emittedValues.add(it)
  }

  fun observe() {
    liveData.observeForever(observer)
  }

  suspend fun resume() {
    assert.invoke(emittedValues)
  }

  fun release() {
    liveData.removeObserver(observer)
  }
}
