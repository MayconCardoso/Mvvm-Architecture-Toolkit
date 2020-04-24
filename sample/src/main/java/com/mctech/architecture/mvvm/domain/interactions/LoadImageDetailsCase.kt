package com.mctech.architecture.mvvm.domain.interactions

import com.mctech.architecture.mvvm.domain.InteractionResult
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.service.ImageService

class LoadImageDetailsCase(
    private val service: ImageService
) {
    suspend fun execute(image: Image): InteractionResult<ImageDetails> {
        try {
            return InteractionResult.Success(service.getImageDetails(image))
        } catch (exception: Throwable) {
            if (exception is ImageException) {
                return InteractionResult.Error(exception)
            }

            return InteractionResult.Error(ImageException.UnknownImageException)
        }
    }
}