package io.github.mayconcardoso.networking

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SecureRequestHandlerTest {

  @Test
  fun `should return a known network error`(): Unit = runBlocking {
    val result = runCatching {
      secureRequest<Int>(suspend { throw Throwable() })
    }.exceptionOrNull()

    assertThat(result).isExactlyInstanceOf(NetworkError.UnknownNetworkingError::class.java)
  }
}