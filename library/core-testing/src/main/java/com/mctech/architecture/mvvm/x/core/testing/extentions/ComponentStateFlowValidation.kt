package com.mctech.architecture.mvvm.x.core.testing.extentions

import com.mctech.architecture.mvvm.x.core.ComponentState

fun List<ComponentState<*>>.assertFlow(vararg expectedState : ComponentState<*>){
    // The count must be the same.
    this.assertCount(expectedState.size)

    // Check all items.
    for ((index, value) in expectedState.withIndex()) {
        when(value){
            is ComponentState.Success -> {
                // The item at the same position must be a Success instance.
                this[index].assertThat().isExactlyInstanceOf(ComponentState.Success::class.java)

                // Get item
                val checkingValue = this[index] as ComponentState.Success<*>

                // It is the same object
                value.result?.assertThat()?.isEqualTo(checkingValue.result)
            }
            is ComponentState.Error -> {
                // The item at the same position must be a Success instance.
                this[index].assertThat().isExactlyInstanceOf(ComponentState.Error::class.java)

                // Get item
                val checkingValue = this[index] as ComponentState.Error

                // It is the same object
                value.reason.assertThat().isEqualTo(checkingValue.reason)
            }
            else -> {
                value.assertThat().isEqualTo(this[index])
            }
        }
    }
}