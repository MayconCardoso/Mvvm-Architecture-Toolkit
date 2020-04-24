package com.mctech.architecture.mvvm.domain.interactions

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.service.ImageService
import com.mctech.architecture.mvvm.x.core.testing.testScenario
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadImageListCaseTest{

    private val service                 = mock<ImageService>()
    private val expectedException       = mock<RuntimeException>()
    private val imageException          = ImageException.CannotFetchImages
    private val expectedValue           = listOf<Image>()

    private lateinit var useCase: LoadImageListCase

    @Before
    fun `before each test`() {
        useCase = LoadImageListCase(service)
    }

    @Test
    fun `should delegate call`() = testScenario(
        scenario = {
            whenever(service.getAllImages()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = {
            verify(service).getAllImages()
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return success`() = testScenario(
        scenario = {
            whenever(service.getAllImages()).thenReturn(expectedValue)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultSuccess(expectedValue)
        }
    )


    @Test
    fun `should return unknown exception`() = testScenario(
        scenario = {
            whenever(service.getAllImages()).thenThrow(expectedException)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultFailure(ImageException.UnknownImageException)
        }
    )

    @Test
    fun `should return known exception`() = testScenario(
        scenario = {
            whenever(service.getAllImages()).thenThrow(imageException)
        },
        action = {
            useCase.execute()
        },
        assertions = { result ->
            result.assertResultFailure(imageException)
        }
    )
}