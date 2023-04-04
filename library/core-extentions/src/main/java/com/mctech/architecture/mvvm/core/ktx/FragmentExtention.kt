package com.mctech.architecture.mvvm.core.ktx

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.mctech.architecture.mvvm.core.BaseViewModel
import com.mctech.architecture.mvvm.core.ViewCommand
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Called to observe any state flow from your view model.
 */
inline fun <T> Fragment.bindState(
  observable: StateFlow<T>,
  crossinline block: (result: T) -> Unit,
) = viewLifecycleOwner.lifecycleScope.launch {
  observable
    .stateIn(viewLifecycleOwner.lifecycleScope)
    .collectLatest {
      block(it)
    }
}

/**
 * Called to observe any live data from your view model.
 */
inline fun <T> Fragment.bindState(
  observable: LiveData<T>,
  crossinline block: (result: T) -> Unit,
) = viewLifecycleOwner.lifecycleScope.launch {
  observable.observe(this@bindState) {
    block(it)
  }
}

/**
 * Called to observe all commands sent from your view model.
 */
fun Fragment.bindCommand(
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  commandObserver(
    lifecycle = viewLifecycleOwner,
    viewModel = viewModel,
    block = block,
  )
}

/**
 * Called to observe only the first command sent from your view mode.
 */
fun Fragment.bindAutoDisposableCommand(
  viewModel: BaseViewModel,
  block: (result: ViewCommand) -> Unit,
) {
  autoDisposeCommandObserver(
    lifecycle = viewLifecycleOwner,
    viewModel = viewModel,
    block = block,
  )
}

/**
 * Delegate to bind the view of a fragment. It can be used from [Fragment.onViewCreated] to
 * [Fragment.onDestroyView] (inclusive).
 *
 * Using this delegate **requires** using the ContentView constructor of [Fragment] passing the contentLayoutId,
 * or calling [Fragment.onCreateView] to inflate the layout.
 *
 * Sample usage:
 * ```
 * class SampleFragment : Fragment(R.layout.fragment_sample) {
 *   private val binding by viewBinding(FragmentSampleBinding::bind)

 *   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *     super.onViewCreated(view, savedInstanceState)
 *     // binding is ready to use
 *   }
 * }
 * ```
 */
fun <T : ViewBinding> Fragment.viewBinding(
  factory: (View) -> T,
): ReadOnlyProperty<Fragment, T> = object : ReadOnlyProperty<Fragment, T> {

  private var binding: T? = null
  private val lifecycleObserver = object : DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
      owner.lifecycle.removeObserver(this)
      binding = null
    }
  }

  override fun getValue(
    thisRef: Fragment,
    property: KProperty<*>,
  ): T = binding ?: factory(requireView()).also {
    // Only schedule clearing and keep a reference if not called from Fragment.onDestroyView()
    if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
      binding = it
    }
  }
}

/**
 * Handy function to perform setup operations during a lifecycle method of the [LifecycleOwner] that won't
 * prevent that lifecycle method from finishing as the given [body] is launched as a coroutine in the [lifecycleScope].
 */
inline fun LifecycleOwner.avoidFrozenFrames(crossinline body: suspend () -> Unit) {
  lifecycleScope.launch { body() }
}

inline fun <reified T> Fragment.attachToParentOrContextOptional(context: Context): T? {
  return when {
    parentFragment is T -> parentFragment as T
    context is T -> context
    else -> null
  }
}
