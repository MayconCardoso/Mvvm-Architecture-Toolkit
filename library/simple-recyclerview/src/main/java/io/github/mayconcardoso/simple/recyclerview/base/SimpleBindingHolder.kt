package io.github.mayconcardoso.simple.recyclerview.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Default view holder to create lists without boilerplate
 */
class SimpleBindingHolder<T, VDB : ViewBinding>(
  val binding: VDB,
) : RecyclerView.ViewHolder(binding.root)