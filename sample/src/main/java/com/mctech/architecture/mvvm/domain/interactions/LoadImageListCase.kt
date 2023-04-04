package com.mctech.architecture.mvvm.domain.interactions

import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.service.ImageService
import javax.inject.Inject

class LoadImageListCase @Inject constructor(
  private val service: ImageService
) {
  suspend fun execute(): InteractionResult<List<Image>> {
    try {
      return InteractionResult.Success(service.getAllImages())
    } catch (exception: Throwable) {
      if (exception is ImageException) {
        return InteractionResult.Error(exception)
      }

      return InteractionResult.Error(ImageException.UnknownImageException)
    }
  }
}