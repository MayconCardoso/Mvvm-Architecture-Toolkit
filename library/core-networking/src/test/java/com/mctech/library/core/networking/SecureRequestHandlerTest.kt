package com.mctech.library.core.networking

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Test

class SecureRequestHandlerTest {

  @Test
  fun `should return a known network error`() {
    runBlocking {

      val result = runCatching {
        secureRequest<Int>(suspend {
          throw Throwable()
        })
      }.exceptionOrNull()

      Assertions.assertThat(result)
        .isExactlyInstanceOf(
          NetworkError.UnknownNetworkingError::class.java
        )
    }
  }
}