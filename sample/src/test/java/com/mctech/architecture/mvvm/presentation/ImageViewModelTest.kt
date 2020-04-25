package com.mctech.architecture.mvvm.presentation


import androidx.lifecycle.viewModelScope
import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.interactions.LoadImageDetailsCase
import com.mctech.architecture.mvvm.domain.interactions.LoadImageListCase
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.testing.BaseViewModelTest
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertFlow
import com.mctech.architecture.mvvm.x.core.testing.extentions.assertIsEmpty
import com.mctech.architecture.mvvm.x.core.testing.extentions.testLiveData
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ImageViewModelTest : BaseViewModelTest(){
    private lateinit var viewModel: ImageViewModel

    private val loadImageDetailsCase    = mock<LoadImageDetailsCase>()
    private val loadImageListCase       = mock<LoadImageListCase>()
    private val expectedList            = mutableListOf<Image>()

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
    fun `should initialize list component`() {
        viewModel.imageListComponent.testLiveData(
            assertion = {
                it.assertFlow(ComponentState.Initializing)
                verifyZeroInteractions(loadImageListCase)
                verifyZeroInteractions(loadImageDetailsCase)
            }
        )
    }

    @Test
    fun `should initialize details component`() {
        viewModel.imageDetailsComponent.assertIsEmpty()
    }

    @Test
    fun `should show data on list component`() {
        viewModel.imageListComponent.testLiveData(
            scenario = {
                whenever(loadImageListCase.execute()).thenReturn(
                    InteractionResult.Success(expectedList)
                )
            },
            action = {
                viewModel.interact(ImageInteraction.LoadImages)
            },
            assertion = {
                it.assertFlow(
                    ComponentState.Initializing,
                    ComponentState.Loading.FromEmpty,
                    ComponentState.Success(expectedList)
                )
                verify(loadImageListCase, times(1)).execute()
            }
        )
    }

    @Test
    fun `should show error on list component`() {
        viewModel.imageListComponent.testLiveData(
            scenario = {
                whenever(loadImageListCase.execute()).thenReturn(
                    InteractionResult.Error(ImageException.CannotFetchImages)
                )
            },
            action = {
                viewModel.interact(ImageInteraction.LoadImages)
            },
            assertion = {
                it.assertFlow(
                    ComponentState.Initializing,
                    ComponentState.Loading.FromEmpty,
                    ComponentState.Error(ImageException.CannotFetchImages)
                )
                verify(loadImageListCase, times(1)).execute()
            }
        )
    }
}