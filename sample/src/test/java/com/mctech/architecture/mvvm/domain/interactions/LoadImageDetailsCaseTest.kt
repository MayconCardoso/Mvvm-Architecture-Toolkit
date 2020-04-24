package com.mctech.architecture.mvvm.domain.interactions

import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import com.mctech.architecture.mvvm.domain.error.ImageException
import com.mctech.architecture.mvvm.domain.service.ImageService
import com.mctech.architecture.mvvm.x.core.testing.testScenario
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoadImageDetailsCaseTest{

    private val service                 = mock<ImageService>()
    private val expectedException       = mock<RuntimeException>()
    private val imageException          = ImageException.CannotFetchImages
    private val expectedValue           = mock<ImageDetails>()
    private val expectedRequest         = mock<Image>()

    private lateinit var useCase: LoadImageDetailsCase

    @Before
    fun `before each test`() {
        useCase = LoadImageDetailsCase(service)
    }

    @Test
    fun `should delegate call`() = testScenario(
        scenario = {
            whenever(service.getImageDetails(any())).thenReturn(expectedValue)
        },
        action = {
            useCase.execute(expectedRequest)
        },
        assertions = {
            verify(service).getImageDetails(expectedRequest)
            verifyNoMoreInteractions(service)
        }
    )

    @Test
    fun `should return success`() = testScenario(
        scenario = {
            whenever(service.getImageDetails(any())).thenReturn(expectedValue)
        },
        action = {
            useCase.execute(expectedRequest)
        },
        assertions = { result ->
            result.assertResultSuccess(expectedValue)
        }
    )


    @Test
    fun `should return unknown exception`() = testScenario(
        scenario = {
            whenever(service.getImageDetails(any())).thenThrow(expectedException)
        },
        action = {
            useCase.execute(expectedRequest)
        },
        assertions = { result ->
            result.assertResultFailure(ImageException.UnknownImageException)
        }
    )

    @Test
    fun `should return known exception`() = testScenario(
        scenario = {
            whenever(service.getImageDetails(any())).thenThrow(imageException)
        },
        action = {
            useCase.execute(expectedRequest)
        },
        assertions = { result ->
            result.assertResultFailure(imageException)
        }
    )
}
