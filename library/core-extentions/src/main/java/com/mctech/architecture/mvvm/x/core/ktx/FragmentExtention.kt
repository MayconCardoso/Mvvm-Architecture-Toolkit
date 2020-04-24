package com.mctech.architecture.mvvm.x.core.ktx

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import kotlinx.coroutines.launch

/**
 * Called to observe any live data from your view model.
 */
fun <T> Fragment.bindData(observable: LiveData<T>, block: (result: T) -> Unit) {
    lifecycleScope.launch {
        observable.observe(this@bindData, Observer {
            block(it)
        })
    }
}

/**
 * Called to observe any component state from your view model.
 */
fun <T> Fragment.bindState(
    observable: LiveData<ComponentState<T>>,
    block: (result: ComponentState<T>) -> Unit
) = bindData(observable, block)

/**
 * Called to observe all commands sent from your view mode.
 */
fun Fragment.bindCommand(viewModel: BaseViewModel, block: (result: ViewCommand) -> Unit) {
    lifecycleScope.launch {
        commandObserver(
            lifecycle = this@bindCommand,
            viewModel = viewModel,
            block = block
        )
    }
}

/**
 * Called to observe only the first command sent from your view mode.
 */
fun Fragment.bindAutoDisposableCommand(viewModel: BaseViewModel, block: (result: ViewCommand) -> Unit) {
    lifecycleScope.launch {
        autoDisposeCommandObserver(
            lifecycle = this@bindAutoDisposableCommand,
            viewModel = viewModel,
            block = block
        )
    }
}