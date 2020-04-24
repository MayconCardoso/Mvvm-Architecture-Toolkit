package com.mctech.architecture.mvvm.domain

sealed class InteractionResult<out T> {
    data class Success<T>(val result : T) : InteractionResult<T>()
    data class Error(val error : Throwable) : InteractionResult<Nothing>()
}