package com.mctech.architecture.mvvm.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.mctech.architecture.mvvm.R
import com.mctech.architecture.mvvm.databinding.FragmentListOfImagesBinding
import com.mctech.architecture.mvvm.databinding.ItemImageBinding
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.presentation.ImageInteraction
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import io.github.mayconcardoso.mvvm.core.ComponentState
import io.github.mayconcardoso.mvvm.core.ktx.bindState
import io.github.mayconcardoso.mvvm.core.ktx.viewBinding
import io.github.mayconcardoso.simple.recyclerview.prepareRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListFragment : Fragment(R.layout.fragment_list_of_images) {

  // region Variables

  /**
   * Holds the feature view model
   */
  private val viewModel by viewModels<ImageViewModel>(
    ownerProducer = { requireActivity() }
  )

  /**
   * Holds the feature view binding
   */
  private val binding by viewBinding(FragmentListOfImagesBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // Start view model flow
    viewModel.initialize()

    // Observes image component state.
    bindState(viewModel.state, ::consumeComponentState)
  }

  // endregion

  // region State Manipulation

  private fun consumeComponentState(state: ComponentState<List<Image>>) = when (state) {
    is ComponentState.Error -> renderErrorState()
    is ComponentState.Loading -> renderLoadingState()
    is ComponentState.Success -> renderSuccessState(state.result)
  }

  private fun renderLoadingState() {
    binding.recyclerList.isVisible = false
    binding.progressState.isVisible = true
    binding.errorComponent.isVisible = false
  }

  private fun renderErrorState() {
    binding.recyclerList.isVisible = false
    binding.progressState.isVisible = false
    binding.errorComponent.isVisible = true
  }

  private fun renderSuccessState(images: List<Image>) {
    binding.recyclerList.isVisible = true
    binding.progressState.isVisible = false
    binding.errorComponent.isVisible = false
    binding.recyclerList.prepareRecyclerView(
      items = images,
      bindView = this::renderImageItem,
      viewHolderFactory = this::createViewHolder,
      diffCallbackFactory = this::createImageDiffAlgorithm,
    )
  }

  private fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): ItemImageBinding {
    return ItemImageBinding.inflate(inflater, parent, false)
  }

  private fun createImageDiffAlgorithm(): DiffUtil.ItemCallback<Image> {
    return object : DiffUtil.ItemCallback<Image>() {
      override fun areItemsTheSame(left: Image, right: Image) = left.id == right.id
      override fun areContentsTheSame(left: Image, right: Image): Boolean {
        return left.title == right.title && left.date == right.date
      }
    }
  }

  private fun renderImageItem(item: Image, binding: ItemImageBinding) {
    binding.tvTitle.text = item.title
    binding.tvDate.text = item.date

    binding.root.setOnClickListener {
      viewModel.interact(ImageInteraction.OpenDetails(item))
    }
  }

  // endregion

}