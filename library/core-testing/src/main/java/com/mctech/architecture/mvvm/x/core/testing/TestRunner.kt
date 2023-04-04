package com.mctech.architecture.mvvm.x.core.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

/**
 * Used only to make your test more readable.
 */
@ExperimentalCoroutinesApi
fun <T> testScenario(
  scenario: suspend () -> Unit = {},
  action: suspend () -> T,
  assertions: suspend (result: T) -> Unit
) = runTest {
  scenario.invoke()
  assertions.invoke(action.invoke())
}