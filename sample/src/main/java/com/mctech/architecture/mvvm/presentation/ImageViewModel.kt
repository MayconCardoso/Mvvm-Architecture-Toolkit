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
import com.mctech.architecture.mvvm.x.core.UserInteraction

class ImageViewModel(
    private val loadImageListCase       : LoadImageListCase,
    private val loadImageDetailsCase    : LoadImageDetailsCase
) : BaseViewModel(){

    private val _imageListComponent = MutableLiveData<ComponentState<List<Image>>>(ComponentState.Initializing)
    val imageListComponent : LiveData<ComponentState<List<Image>>> = _imageListComponent

    private val _imageDetailsComponent = MutableLiveData<ComponentState<ImageDetails>>()
    val imageDetailsComponent : LiveData<ComponentState<ImageDetails>> = _imageDetailsComponent

    override suspend fun handleUserInteraction(interaction: UserInteraction) {
        when(interaction){
            is ImageInteraction.LoadImages  -> loadImagesInteraction()
            is ImageInteraction.OpenDetails -> openImageDetailsInteraction(interaction.image)
        }
    }

    private suspend fun loadImagesInteraction() {
        // Set the list component with 'loading' state.
        _imageListComponent.value = ComponentState.Loading.FromEmpty

        // Load image's details.
        when(val listResult = loadImageListCase.execute()){
            // Set the list component with 'Success' state.
            is InteractionResult.Success -> {
                _imageListComponent.value = ComponentState.Success(listResult.result)
            }

            // Set the list component with 'Error' state.
            is InteractionResult.Error -> {
                _imageListComponent.value = ComponentState.Error(listResult.error)
            }
        }
    }

    private suspend fun openImageDetailsInteraction(image: Image) {
        // Set the details component with 'loading' state.
        _imageDetailsComponent.value = ComponentState.Loading.FromEmpty

        // Open the details screen.
        sendCommand(ImageCommands.OpenImageDetails)

        // Load image's details.
        when(val detailsResult = loadImageDetailsCase.execute(image)){
            // Set the details component with 'Success' state.
            is InteractionResult.Success -> {
                _imageDetailsComponent.value = ComponentState.Success(detailsResult.result)
            }

            // Set the details component with 'Error' state.
            is InteractionResult.Error -> {
                _imageDetailsComponent.value = ComponentState.Error(detailsResult.error)
            }
        }
    }
}