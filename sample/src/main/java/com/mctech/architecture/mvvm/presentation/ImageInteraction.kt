package com.mctech.architecture.mvvm.presentation

import com.mctech.architecture.mvvm.core.UserInteraction
import com.mctech.architecture.mvvm.domain.entities.Image

sealed class ImageInteraction : UserInteraction{
    object LoadImages : ImageInteraction()
    data class OpenDetails(val image: Image) : ImageInteraction()
}