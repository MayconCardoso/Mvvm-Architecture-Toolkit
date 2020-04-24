package com.mctech.architecture.mvvm.data

import com.mctech.architecture.mvvm.domain.service.ImageService

class ImageRepository(dataSource : ImageDataSource) : ImageService by dataSource