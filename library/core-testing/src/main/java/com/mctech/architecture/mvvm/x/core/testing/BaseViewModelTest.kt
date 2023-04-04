package com.mctech.architecture.mvvm.x.core.testing

import com.mctech.architecture.mvvm.x.core.testing.rules.CoroutinesMainTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

abstract class BaseViewModelTest {
  @get:Rule
  val coroutinesTestRule = CoroutinesMainTestRule()
}
