package com.mctech.architecture.mvvm.x.core.testing.extentions

import com.mctech.architecture.mvvm.x.core.ComponentState
import org.assertj.core.api.Assertions.assertThat


fun List<ComponentState<*>>.assertFlow(vararg expectedState: ComponentState<*>) {
  // The count must be the same.
  assertThat(size).isEqualTo(expectedState.size)

  // Check all items.
  for ((index, value) in expectedState.withIndex()) {
    when (value) {
      is ComponentState.Success -> {
        // The item at the same position must be a Success instance.
        assertThat(this[index]).isExactlyInstanceOf(ComponentState.Success::class.java)

        // Get item
        val checkingValue = this[index] as ComponentState.Success<*>

        // It is the same object
        assertThat(value.result).isEqualTo(checkingValue.result)
      }
      is ComponentState.Error -> {
        // The item at the same position must be a Success instance.
        assertThat(this[index]).isExactlyInstanceOf(ComponentState.Error::class.java)

        // Get item
        val checkingValue = this[index] as ComponentState.Error

        // It is the same object
        assertThat(value.reason).isEqualTo(checkingValue.reason)
      }
      else -> {
        assertThat(value).isEqualTo(this[index])
      }
    }
  }
}