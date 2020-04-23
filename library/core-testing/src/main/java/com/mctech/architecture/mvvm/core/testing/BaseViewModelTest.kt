package com.mctech.architecture.mvvm.core.testing

import com.mctech.architecture.mvvm.core.testing.rules.CoroutinesMainTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest  {
    @get:Rule
    val coroutinesTestRule = CoroutinesMainTestRule()
}