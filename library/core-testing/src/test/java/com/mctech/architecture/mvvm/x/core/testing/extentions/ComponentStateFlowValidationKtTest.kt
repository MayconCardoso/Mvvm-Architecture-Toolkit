package com.mctech.architecture.mvvm.x.core.testing.extentions

import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.testing.testScenario
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class ComponentStateFlowValidationKtTest{
    private val expectedValue       = "Hello world"
    private val expectedError       = RuntimeException()

    private val expectedInitializationFLow = mutableListOf<ComponentState<String>>().apply {
        add(ComponentState.Initializing)
    }.toList()

    private val expectedSuccessFLow = mutableListOf<ComponentState<String>>().apply {
        add(ComponentState.Initializing)
        add(ComponentState.Loading.FromEmpty)
        add(ComponentState.Success(expectedValue))
    }.toList()

    private val expectedErrorFLow   = mutableListOf<ComponentState<String>>().apply {
        add(ComponentState.Initializing)
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
                ComponentState.Initializing,
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
                ComponentState.Initializing,
                ComponentState.Loading.FromEmpty,
                ComponentState.Error(expectedError)
            )
        }
    )

    @Test
    fun `should assert initialization flow`() = testScenario(
        action = {
            expectedInitializationFLow
        },
        assertions = { stateFlow ->
            stateFlow.assertFlow(
                ComponentState.Initializing
            )
        }
    )
}