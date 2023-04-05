package io.github.mayconcardoso.mvvm.core.testing.agent

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FlowObserverAgent<T>(
  private val flow: Flow<T>,
  private val assert: suspend (List<T>) -> Unit,
) {

  private val results = mutableListOf<T>()
  private var job: Job? = null

  fun collect(scope: CoroutineScope) {
    job = flow
      .onEach(results::add)
      .launchIn(scope)
  }

  suspend fun resume() {
    assert(results)
  }

  fun release() {
    job?.cancel()
  }
}
