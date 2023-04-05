package io.github.mayconcardoso.mvvm.core.ktx

import androidx.lifecycle.LifecycleOwner
import io.github.mayconcardoso.mvvm.core.BaseViewModel
import io.github.mayconcardoso.mvvm.core.SingleLiveEvent
import io.github.mayconcardoso.mvvm.core.ViewCommand

/**
 * It is called when you wanna observe a single event and then stop to observing it.
 */
internal fun autoDisposeCommandObserver(
  lifecycle: LifecycleOwner,
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  val key = lifecycle.toString()
  val commandObservable = ((viewModel.commandObservable) as SingleLiveEvent<ViewCommand>)
  commandObservable.observe(key, lifecycle) {
    block(it)
    commandObservable.removeObserver(key)
  }
}

/**
 * It is called when you wanna observe all commands while the lifecycle owner is activated.
 */
internal fun commandObserver(
  lifecycle: LifecycleOwner,
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  ((viewModel.commandObservable) as SingleLiveEvent<ViewCommand>).observe(
    lifecycle.toString(),
    lifecycle,
  ) {
    block(it)
  }
}
