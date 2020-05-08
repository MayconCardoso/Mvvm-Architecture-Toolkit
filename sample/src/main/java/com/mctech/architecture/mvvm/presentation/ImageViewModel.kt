package com.mctech.architecture.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import com.mctech.architecture.mvvm.domain.interactions.LoadImageDetailsCase
import com.mctech.architecture.mvvm.domain.interactions.LoadImageListCase
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.OnInteraction
import com.mctech.architecture.mvvm.x.core.UserInteraction
import com.mctech.architecture.mvvm.x.core.ktx.changeToErrorState
import com.mctech.architecture.mvvm.x.core.ktx.changeToListLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToLoadingState
import com.mctech.architecture.mvvm.x.core.ktx.changeToSuccessState

class ImageViewModel(
    private val loadImageListCase       : LoadImageListCase,
    private val loadImageDetailsCase    : LoadImageDetailsCase
) : BaseViewModel(){

    private val _imageListComponent = MutableLiveData<ComponentState<List<Image>>>(ComponentState.Initializing)
    val imageListComponent : LiveData<ComponentState<List<Image>>> = _imageListComponent

    private val _imageDetailsComponent = MutableLiveData<ComponentState<ImageDetails>>()
    val imageDetailsComponent : LiveData<ComponentState<ImageDetails>> = _imageDetailsComponent

    @OnInteraction(ImageInteraction.OpenDetails::class)
    private suspend fun openImageDetailsInteraction(interaction : ImageInteraction.OpenDetails) {
        // Set the details component with 'loading' state.
        _imageDetailsComponent.changeToLoadingState()

        // Open the details screen.
        sendCommand(ImageCommands.OpenImageDetails)

        // Load image's details.
        when(val detailsResult = loadImageDetailsCase.execute(interaction.image)){
            // Set the details component with 'Success' state.
            is InteractionResult.Success -> {
                _imageDetailsComponent.changeToSuccessState(detailsResult.result)
            }

            // Set the details component with 'Error' state.
            is InteractionResult.Error -> {
                _imageDetailsComponent.changeToErrorState(detailsResult.error)
            }
        }
    }

    @OnInteraction(ImageInteraction.LoadImages::class)
    private suspend fun loadImagesInteraction() {
        // Set the list component with 'loading' state.
        _imageListComponent.changeToListLoadingState()

        // Load image's details.
        when(val listResult = loadImageListCase.execute()){
            // Set the list component with 'Success' state.
            is InteractionResult.Success -> {
                _imageListComponent.changeToSuccessState(listResult.result)
            }

            // Set the list component with 'Error' state.
            is InteractionResult.Error -> {
                _imageListComponent.changeToErrorState(listResult.error)
            }
        }
    }
}