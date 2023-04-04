package com.mctech.architecture.mvvm.presentation

import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import com.mctech.architecture.mvvm.domain.interactions.LoadImageDetailsCase
import com.mctech.architecture.mvvm.domain.interactions.LoadImageListCase
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.OnInteraction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
  private val loadImageListCase: LoadImageListCase,
  private val loadImageDetailsCase: LoadImageDetailsCase
) : BaseViewModel() {

  private val _state by lazy {
    MutableStateFlow<ComponentState<List<Image>>>(ComponentState.Loading.FromEmpty)
  }
  val state: StateFlow<ComponentState<List<Image>>> by lazy { _state }

  private val _detailState by lazy {
    MutableStateFlow<ComponentState<ImageDetails>>(ComponentState.Loading.FromEmpty)
  }
  val detailState: StateFlow<ComponentState<ImageDetails>> by lazy { _detailState }

  override suspend fun initializeComponents() {
    loadImagesInteraction()
  }

  private suspend fun loadImagesInteraction() {
    when (val listResult = loadImageListCase.execute()) {
      // Set the list component with 'Success' state.
      is InteractionResult.Success -> {
        _state.value = ComponentState.Success(listResult.result)
      }

      // Set the list component with 'Error' state.
      is InteractionResult.Error -> {
        _state.value = ComponentState.Error(listResult.error)
      }
    }
  }

  @OnInteraction(ImageInteraction.OpenDetails::class)
  private suspend fun openImageDetailsInteraction(interaction: ImageInteraction.OpenDetails) {
    // Set the details component with 'loading' state.
    _detailState.value = ComponentState.Loading.FromEmpty

    // Open the details screen.
    sendCommand(ImageCommands.OpenImageDetails)

    // Load image's details.
    when (val detailsResult = loadImageDetailsCase.execute(interaction.image)) {
      // Set the details component with 'Success' state.
      is InteractionResult.Success -> {
        _detailState.value = ComponentState.Success(detailsResult.result)
      }

      // Set the details component with 'Error' state.
      is InteractionResult.Error -> {
        _detailState.value = ComponentState.Error(detailsResult.error)
      }
    }
  }

}