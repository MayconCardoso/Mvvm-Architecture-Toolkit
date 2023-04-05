package io.github.mayconcardoso.mvvm.core.testing.extentions

import io.github.mayconcardoso.mvvm.core.ComponentState
import io.github.mayconcardoso.mvvm.core.testing.extentions.assertFlow
import io.github.mayconcardoso.mvvm.core.testing.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class ComponentStateFlowValidationKtTest {
  private val expectedValue = "Hello world"
  private val expectedError = RuntimeException()

  private val expectedSuccessFLow = mutableListOf<ComponentState<String>>().apply {
    add(ComponentState.Loading.FromEmpty)
    add(ComponentState.Success(expectedValue))
  }.toList()

  private val expectedErrorFLow = mutableListOf<ComponentState<String>>().apply {
    add(ComponentState.Loading.FromEmpty)
    add(ComponentState.Error(expectedError))
  }.toList()

  @Test
  fun `should assert success flow`() = testScenario(
    action = {
      expectedSuccessFLow
    },
    assertions = { stateFlow ->
      stateFlow.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedValue)
      )
    }
  )

  @Test
  fun `should assert error flow`() = testScenario(
    action = {
      expectedErrorFLow
    },
    assertions = { stateFlow ->
      stateFlow.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Error<String>(expectedError)
      )
    }
  )
}