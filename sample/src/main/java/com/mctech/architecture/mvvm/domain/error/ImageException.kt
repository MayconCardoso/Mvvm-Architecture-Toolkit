package com.mctech.architecture.mvvm.domain.error

sealed class ImageException         : RuntimeException(){
    object CannotFetchImages        : ImageException()
    object UnknownImageException    : ImageException()
}