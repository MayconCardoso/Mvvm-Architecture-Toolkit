package io.github.mayconcardoso.simple.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import androidx.viewbinding.ViewBinding
import io.github.mayconcardoso.simple.recyclerview.base.SimpleBindingAdapter
import io.github.mayconcardoso.simple.recyclerview.base.SimpleBindingHolder
import io.github.mayconcardoso.simple.recyclerview.utils.SimpleItemDiffCallback

/**
 * Encapsulate the creation of recycler views in order to reduce all the necessary boilerplate.
 */
fun <T : Any, VDB : ViewBinding> RecyclerView.prepareRecyclerView(
  // Data that will be initially rendered.
  items: List<T> = listOf(),

  // Diff algorithm
  diffCallbackFactory: () -> DiffUtil.ItemCallback<T> = {
    SimpleItemDiffCallback()
  },

  // Setup recycler view callback.
  setupRecyclerView: (RecyclerView) -> Unit = { recyclerView ->
    recyclerView.setHasFixedSize(true)
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.layoutManager = LinearLayoutManager(context).apply {
      orientation = RecyclerView.VERTICAL
    }
  },

  // Prepare view binding
  viewHolderFactory: (parent: ViewGroup, inflater: LayoutInflater) -> VDB,

  // Delegate to bind the item on  the view holder.
  bindView: (item: T, viewBinding: VDB) -> Unit,

  // Called when list is done
  onAdapterAttached: (adapter: SimpleBindingAdapter<T, VDB>) -> Unit = {}
) {
  // If adapter has already been attached.
  // We just update the content.
  if (adapter != null) {
    (adapter as ListAdapter<T, SimpleBindingHolder<T, *>>).submitList(items)
    return
  }

  // Setup recycler
  setupRecyclerView(this)

  // Create a new adapter.
  val adapter = SimpleBindingAdapter(
    bindView = bindView,
    diffCallback = diffCallbackFactory(),
    viewHolderFactory = viewHolderFactory,
  )

  // Attach it on the recycler view.
  this.adapter = adapter.apply {
    submitList(items)
  }

  // Inform the client that everything is set.
  onAdapterAttached.invoke(adapter)
}