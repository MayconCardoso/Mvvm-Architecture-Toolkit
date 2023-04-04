package com.mctech.architecture.mvvm.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mctech.architecture.mvvm.R
import com.mctech.architecture.mvvm.databinding.FragmentDetailsOfImageBinding
import com.mctech.architecture.mvvm.domain.entities.ImageDetails
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import com.mctech.architecture.mvvm.core.ComponentState
import com.mctech.architecture.mvvm.core.ktx.bindState
import com.mctech.architecture.mvvm.core.ktx.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailsFragment : Fragment(R.layout.fragment_details_of_image) {

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
  private val binding by viewBinding(FragmentDetailsOfImageBinding::bind)

  // endregion

  // region Lifecycle

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // Start view model flow
    viewModel.initialize()

    // Observes image component state.
    bindState(viewModel.detailState, ::consumeComponentState)
  }

  // endregion

  // region State Manipulation

  private fun consumeComponentState(state: ComponentState<ImageDetails>) = when (state) {
    is ComponentState.Error -> renderErrorState()
    is ComponentState.Loading -> renderLoadingState()
    is ComponentState.Success -> renderSuccessState(state.result)
  }

  private fun renderLoadingState() {
    binding.progressState.isVisible = true
    binding.successContainer.isVisible = false
  }

  private fun renderErrorState() {
    binding.progressState.isVisible = false
    binding.successContainer.isVisible = false
  }

  private fun renderSuccessState(details: ImageDetails) {
    binding.progressState.isVisible = false
    binding.successContainer.isVisible = true
    binding.tvTitle.text = details.description
  }

  // endregion

}