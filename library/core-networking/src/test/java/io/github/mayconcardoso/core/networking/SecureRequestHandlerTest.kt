package io.github.mayconcardoso.core.networking

import io.github.mayconcardoso.core.networking.NetworkError
import io.github.mayconcardoso.core.networking.secureRequest
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