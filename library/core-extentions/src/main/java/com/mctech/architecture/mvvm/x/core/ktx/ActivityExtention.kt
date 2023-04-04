package com.mctech.architecture.mvvm.x.core.ktx

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.mctech.architecture.mvvm.x.core.BaseViewModel
import com.mctech.architecture.mvvm.x.core.ViewCommand
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Called to observe any state flow from your view model.
 */
inline fun <T> AppCompatActivity.bindState(
  observable: StateFlow<T>,
  crossinline block: (result: T) -> Unit,
) = lifecycleScope.launch {
  observable
    .stateIn(lifecycleScope)
    .collectLatest {
      block(it)
    }
}

/**
 * Called to observe any live data from your view model.
 */
inline fun <T> AppCompatActivity.bindState(
  observable: LiveData<T>,
  crossinline block: (result: T) -> Unit,
) = lifecycleScope.launch {
  observable
    .observe(this@bindState) {
      block(it)
    }
}

/**
 * Called to observe all commands sent from your view mode.
 */
fun AppCompatActivity.bindCommand(
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  commandObserver(
    lifecycle = this@bindCommand,
    viewModel = viewModel,
    block = block,
  )
}

/**
 * Called to observe only the first command sent from your view mode.
 */
fun AppCompatActivity.bindAutoDisposableCommand(
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  autoDisposeCommandObserver(
    lifecycle = this@bindAutoDisposableCommand,
    viewModel = viewModel,
    block = block,
  )
}

/**
 * Delegate to bind the view of an activity. It can be used from [AppCompatActivity.onCreate] to
 * [AppCompatActivity.onDestroy] (inclusive).
 *
 * Sample usage:
 * ```
 * class SampleActivity : AppCompatActivity() {
 *   private val binding by viewBinding(ActivitySampleBinding::inflate)
 *
 *   override fun onCreate(savedInstanceState: Bundle?) {
 *     super.onCreate(savedInstanceState)
 *     setContentView(binding.root)
 *     // binding is ready to use
 *   }
 * }
 * ```
 */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
  crossinline factory: (LayoutInflater) -> T,
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
  factory(layoutInflater)
}