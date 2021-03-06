package com.mctech.architecture.mvvm.presentation

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.x.core.UserInteraction

sealed class ImageInteraction : UserInteraction{
    object LoadImages : ImageInteraction()
    data class OpenDetails(val image: Image) : ImageInteraction()
}