package com.mctech.architecture.mvvm.presentation

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.core.UserInteraction

sealed class ImageInteraction : UserInteraction {
  data class OpenDetails(val image: Image) : ImageInteraction()

}