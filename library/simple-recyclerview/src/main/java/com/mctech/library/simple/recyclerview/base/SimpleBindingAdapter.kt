package com.mctech.library.simple.recyclerview.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.mctech.library.simple.recyclerview.utils.SimpleItemDiffCallback

/**
 * Default adapter to create lists without boilerplate.
 */
class SimpleBindingAdapter<T : Any, VDB : ViewBinding>(
  // Diff algorithm
  diffCallback: DiffUtil.ItemCallback<T> = SimpleItemDiffCallback(),

  // Prepare view binding.
  private val viewHolderFactory: (parent: ViewGroup, inflater: LayoutInflater) -> VDB,

  // Delegate to bind the item on  the view holder.
  private val bindView: (item: T, viewBinding: VDB) -> Unit,

  ) : ListAdapter<T, SimpleBindingHolder<T, VDB>>(diffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleBindingHolder<T, VDB>(
    viewHolderFactory(parent, LayoutInflater.from(parent.context))
  )

  override fun onBindViewHolder(holder: SimpleBindingHolder<T, VDB>, position: Int) {
    bindView(getItem(position), holder.binding)
  }
}
