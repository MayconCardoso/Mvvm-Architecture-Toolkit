package com.mctech.architecture.mvvm.x.core.ktx

import androidx.lifecycle.MutableLiveData
import com.mctech.architecture.mvvm.x.core.ComponentState

fun <T> MutableLiveData<ComponentState<List<T>>>.changeToListLoadingState() {
    when(this.value){
        is ComponentState.Success -> {
            this.value = (this.value as ComponentState.Success<List<T>>).getLoadingState()
        }
        else ->{
            this.value = ComponentState.Loading.FromEmpty
        }
    }
}

fun <T> MutableLiveData<ComponentState<T>>.changeToLoadingState() {
    this.value = ComponentState.Loading.FromEmpty
}

fun <T> MutableLiveData<ComponentState<T>>.changeToSuccessState(successValue : T) {
    this.value = ComponentState.Success(successValue)
}

fun <T> MutableLiveData<ComponentState<T>>.changeToErrorState(exception: Throwable) {
    this.value = ComponentState.Error(exception)
}

/**
 * Resolve the LoadingState according the list data.
 */
fun ComponentState<List<*>>.getLoadingState() : ComponentState.Loading{
    return when(this){
        is ComponentState.Success -> {
            if(result.isEmpty())
                ComponentState.Loading.FromEmpty
            else
                ComponentState.Loading.FromData
        }
        else -> {
            ComponentState.Loading.FromEmpty
        }
    }
}