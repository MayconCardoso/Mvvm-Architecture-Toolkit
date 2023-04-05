package io.github.mayconcardoso.simple.recyclerview.utils

import androidx.recyclerview.widget.DiffUtil
import java.util.Objects

class SimpleItemDiffCallback<T : Any>(
  private val itemsTheSame: (oldItem: T, newItem: T) -> Boolean = { oldItem, newItem -> oldItem == newItem },
  private val contentsTheSame: (oldItem: T, newItem: T) -> Boolean = { oldItem, newItem -> Objects.equals(oldItem, newItem) },
) : DiffUtil.ItemCallback<T>() {

  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = itemsTheSame(oldItem, newItem)

  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = contentsTheSame(oldItem, newItem)
}
