package com.mctech.architecture.mvvm.domain.service

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails

interface ImageService {
  suspend fun getAllImages(): List<Image>
  suspend fun getImageDetails(image: Image): ImageDetails
}