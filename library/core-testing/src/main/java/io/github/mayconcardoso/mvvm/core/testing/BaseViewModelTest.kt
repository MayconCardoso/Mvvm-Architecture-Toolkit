package io.github.mayconcardoso.mvvm.core.testing

import io.github.mayconcardoso.mvvm.core.testing.rules.CoroutinesMainTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class BaseViewModelTest {
  @get:Rule
  val coroutinesTestRule = CoroutinesMainTestRule()
}
