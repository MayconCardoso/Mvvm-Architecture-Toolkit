package com.mctech.architecture.mvvm.core.ktx

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mctech.architecture.mvvm.core.BaseViewModel
import com.mctech.architecture.mvvm.core.ComponentState
import com.mctech.architecture.mvvm.core.ViewCommand
import kotlinx.coroutines.launch

/**
 * Called to observe any live data from your view model.
 */
fun <T> AppCompatActivity.bindData(observable: LiveData<T>, block: (result: T) -> Unit) {
    lifecycleScope.launch {
        observable.observe(this@bindData, Observer {
            block(it)
        })
    }
}

/**
 * Called to observe any component state from your view model.
 */
fun <T> AppCompatActivity.bindState(
    observable: LiveData<ComponentState<T>>,
    block: (result: ComponentState<T>) -> Unit
) = bindData(observable, block)

/**
 * Called to observe all commands sent from your view mode.
 */
fun AppCompatActivity.bindCommand(viewModel: BaseViewModel, block: (result: ViewCommand) -> Unit) {
    lifecycleScope.launch { commandObserver(
            lifecycle   = this@bindCommand,
            viewModel   = viewModel,
            block       = block
    )}
}

/**
 * Called to observe only the first command sent from your view mode.
 */
fun AppCompatActivity.bindAutoDisposableCommand(viewModel: BaseViewModel, block: (result: ViewCommand) -> Unit) {
    lifecycleScope.launch { autoDisposeCommandObserver(
            lifecycle   = this@bindAutoDisposableCommand,
            viewModel   = viewModel,
            block       = block
    )}
}