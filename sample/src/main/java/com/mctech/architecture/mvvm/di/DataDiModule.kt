package com.mctech.architecture.mvvm.di

import com.mctech.architecture.mvvm.data.ImageDataSource
import com.mctech.architecture.mvvm.data.ImageMockedDataSource
import com.mctech.architecture.mvvm.data.ImageRepository
import com.mctech.architecture.mvvm.domain.service.ImageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

  @Provides
  fun providesImageDataSource(): ImageDataSource = ImageMockedDataSource()

  @Provides
  fun providesImageService(dataSource: ImageDataSource): ImageService = ImageRepository(
    dataSource
  )

}