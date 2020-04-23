package com.mctech.architecture.mvvm.core.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

/**
 * Used only to make your test more readable.
 */
@ExperimentalCoroutinesApi
fun <T> testScenario(
    scenario: suspend () -> Unit = {},
    action: suspend () -> T,
    assertions: suspend (result: T) -> Unit
) = runBlockingTest {
    scenario.invoke()
    assertions.invoke(action.invoke())
}