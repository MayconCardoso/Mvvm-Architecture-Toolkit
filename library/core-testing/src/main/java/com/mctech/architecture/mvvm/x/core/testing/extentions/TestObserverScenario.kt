package com.mctech.architecture.mvvm.x.core.testing.extentions

import androidx.lifecycle.LiveData
import com.mctech.architecture.mvvm.x.core.testing.agent.FlowObserverAgent
import com.mctech.architecture.mvvm.x.core.testing.agent.LiveDataObserverAgent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class TestObserverScenario {
  private val flowAgents = mutableListOf<FlowObserverAgent<*>>()
  private val liveDataAgents = mutableListOf<LiveDataObserverAgent<*>>()
  private var action: (suspend () -> Unit)? = null
  private var scenario: (suspend () -> Unit)? = null
  private var assertion: (suspend () -> Unit)? = null

  fun givenScenario(scenario: suspend () -> Unit) {
    if (this.scenario != null) {
      throw IllegalArgumentException("You must have only one scenario by test.")
    }

    this.scenario = scenario
  }

  fun whenAction(action: suspend () -> Unit) {
    if (this.action != null) {
      throw IllegalArgumentException("You must have only one action by test.")
    }

    this.action = action
  }

  fun thenAssert(assert: suspend () -> Unit) {
    if (this.assertion != null) {
      throw IllegalArgumentException("You must have only one assertion block by test.")
    }

    assertion = assert
  }

  fun <T> thenAssertFlow(flow: Flow<T>, assert: suspend (List<T>) -> Unit) {
    flowAgents.add(
      FlowObserverAgent(flow, assert),
    )
  }

  fun <T> thenAssertFlowContainsExactly(flow: Flow<T>, vararg values: T) {
    thenAssertFlow(flow) { data ->
      assertThat(data).containsExactly(*values)
    }
  }

  fun <T> thenAssertFlowIsEmpty(flow: Flow<T>) {
    thenAssertFlow(flow) { data ->
      assertThat(data).isEmpty()
    }
  }

  fun <T> thenAssertLiveData(liveData: LiveData<T>, assert: suspend (List<T>) -> Unit) {
    liveDataAgents.add(
      LiveDataObserverAgent(liveData, assert),
    )
  }

  fun <T> thenAssertLiveDataContainsExactly(liveData: LiveData<T>, vararg values: T) {
    thenAssertLiveData(liveData) { data ->
      assertThat(data).containsExactly(*values)
    }
  }

  fun <T> thenAssertLiveDataFlowIsEmpty(liveData: LiveData<T>) {
    thenAssertLiveData(liveData) { data ->
      assertThat(data).isEmpty()
    }
  }

  private fun execute(context: CoroutineContext) {
    try {
      runTest(context) {
        // Prepare test
        scenario?.invoke()

        // Start commands collection
        liveDataAgents.forEach {
          it.observe()
        }

        // Start states collection
        val scope = CoroutineScope(UnconfinedTestDispatcher(testScheduler))
        flowAgents.forEach {
          it.collect(scope)
        }

        // Action trigger
        action?.invoke()

        // Notify
        liveDataAgents.forEach {
          it.resume()
        }

        testScheduler.advanceUntilIdle()
        flowAgents.forEach {
          it.resume()
        }

        assertion?.invoke()
      }
    } finally {
      liveDataAgents.forEach {
        it.release()
      }
      flowAgents.forEach {
        it.release()
      }
    }
  }

  companion object {
    fun observerScenario(
      context: CoroutineContext = EmptyCoroutineContext,
      block: TestObserverScenario.() -> Unit,
    ) {
      TestObserverScenario()
        .apply(block)
        .execute(context)
    }
  }
}
