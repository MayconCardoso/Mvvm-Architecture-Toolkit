package com.mctech.architecture.mvvm.presentation


import androidx.lifecycle.viewModelScope
import io.github.mayconcardoso.mvvm.core.ComponentState
import io.github.mayconcardoso.mvvm.core.testing.BaseViewModelTest
import io.github.mayconcardoso.mvvm.core.testing.extentions.TestObserverScenario.Companion.observerScenario
import io.github.mayconcardoso.mvvm.core.testing.extentions.assertFlow
import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.interactions.LoadImageDetailsCase
import com.mctech.architecture.mvvm.domain.interactions.LoadImageListCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ImageViewModelTest : BaseViewModelTest() {
  private lateinit var viewModel: ImageViewModel

  private val loadImageDetailsCase = mock<LoadImageDetailsCase>()
  private val loadImageListCase = mock<LoadImageListCase>()
  private val expectedList = mutableListOf<Image>()

  @Before
  fun `before each test`() {
    viewModel = ImageViewModel(
      loadImageListCase,
      loadImageDetailsCase
    )
  }

  @After
  fun `after each test`() {
    viewModel.viewModelScope.cancel()
  }

  @Test
  fun `should initialize components`() = observerScenario {
    thenAssertFlow(viewModel.state) {
      it.assertFlow(ComponentState.Loading.FromEmpty)
    }

    thenAssertFlow(viewModel.detailState) {
      it.assertFlow(ComponentState.Loading.FromEmpty)
    }
  }

  @Test
  fun `should show data on list component`() = observerScenario {
    givenScenario {
      whenever(loadImageListCase.execute()).thenReturn(
        InteractionResult.Success(expectedList)
      )
    }

    whenAction {
      viewModel.initialize()
    }

    thenAssertFlow(viewModel.state) {
      it.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Success(expectedList)
      )
      verify(loadImageListCase, times(1)).execute()
    }
  }

  @Test
  fun `should show error on list component`() = observerScenario {
    givenScenario {
      whenever(loadImageListCase.execute()).thenReturn(
        InteractionResult.Error(ImageException.CannotFetchImages)
      )
    }

    whenAction {
      viewModel.initialize()
    }

    thenAssertFlow(viewModel.state) {
      it.assertFlow(
        ComponentState.Loading.FromEmpty,
        ComponentState.Error<List<Image>>(ImageException.CannotFetchImages)
      )
      verify(loadImageListCase, times(1)).execute()
    }
  }
}