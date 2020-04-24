package com.mctech.architecture.mvvm.domain.interactions

import com.mctech.architecture.mvvm.domain.InteractionResult
import org.assertj.core.api.Assertions

fun InteractionResult<*>.assertResultFailure(expectedException: Exception){
    val resultException = (this as InteractionResult.Error).error
    Assertions.assertThat(this).isInstanceOf(InteractionResult.Error::class.java)
    Assertions.assertThat(resultException).isEqualTo(expectedException)
}

fun <T> InteractionResult<T>.assertResultSuccess(expectedValue : T){
    val expectedResult = InteractionResult.Success(expectedValue)
    val entity = (this as InteractionResult.Success).result

    Assertions.assertThat(this)
            .isExactlyInstanceOf(InteractionResult.Success::class.java)
            .isEqualTo(expectedResult)

    Assertions.assertThat(entity).isEqualTo(expectedValue)
}