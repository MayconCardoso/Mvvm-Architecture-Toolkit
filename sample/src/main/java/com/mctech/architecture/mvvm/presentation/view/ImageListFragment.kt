package com.mctech.architecture.mvvm.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mctech.architecture.mvvm.R
import com.mctech.architecture.mvvm.databinding.FragmentListOfImagesBinding
import com.mctech.architecture.mvvm.databinding.ItemImageBinding
import com.mctech.architecture.mvvm.domain.entities.Image
import com.mctech.architecture.mvvm.presentation.ImageCommands
import com.mctech.architecture.mvvm.presentation.ImageInteraction
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.architecture.mvvm.x.core.ViewCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindCommand
import com.mctech.architecture.mvvm.x.core.ktx.bindState
import com.mctech.library.view.ktx.createBindingRecyclerView
import com.mctech.library.view.ktx.refreshBindingItems
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImageListFragment : Fragment() {

    private val viewModel   : ImageViewModel by sharedViewModel()
    private var binding     : FragmentListOfImagesBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentListOfImagesBinding.inflate(inflater, container, false).let {
            binding = it
            binding?.viewModel = viewModel
            binding?.lifecycleOwner = this
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindCommand(viewModel) { command ->
            handleCommand(command)
        }

        bindState(viewModel.imageListComponent) {
            renderImageList(it)
        }
    }

    private fun handleCommand(command: ViewCommand) {
        when (command) {
            is ImageCommands.OpenImageDetails -> {
                openDetailScreen()
            }
        }
    }

    private fun openDetailScreen() {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.containerFragment, ImageDetailsFragment())
            ?.commit()
    }

    private fun renderImageList(listState: ComponentState<List<Image>>) {
        when (listState) {
            is ComponentState.Initializing -> {
                // The component has not been initialized yet.
                // It is probably the first time opening this screen.
                // So we are gonna fetch all images.
                // But, if you rotate the screen. The ViewModel will keep the same.
                // So the state of this component will also keep the same and will not init again.
                viewModel.interact(ImageInteraction.LoadImages)
            }
            is ComponentState.Loading -> {
                // If you are not using the DataBinding feature
                // you could handle your views here when your list is loading.
                // Check 'ViewStateBindingAdapter#showOnLoading' method.
            }
            is ComponentState.Error -> {
                // If you are not using the DataBinding feature
                // you could handle your views here when your list is loading.
                // Check 'ViewStateBindingAdapter#showOnError' method.
            }
            is ComponentState.Success -> {
                // I am using the DataBinding feature, so I do not need to 'show' the list visibility
                // I just need to setup my recycler view here.
                setUpList(listState.result)
            }
        }
    }

    private fun setUpList(images: List<Image>) {
        binding?.recyclerList?.also { recyclerView ->
            // Create first list.
            if (recyclerView.adapter == null) {
                createImageList(recyclerView, images)
            }

            // Update current list.
            else {
                updateImageList(recyclerView, images)
            }
        }
    }

    private fun updateImageList(recyclerView: RecyclerView, images: List<Image>) {
        recyclerView.refreshBindingItems(
            newItems = images,
            callback = object : DiffUtil.ItemCallback<Image>() {
                override fun areItemsTheSame(left: Image, right: Image) = left.id == right.id

                override fun areContentsTheSame(left: Image, right: Image): Boolean {
                    return left.title == right.title && left.date == right.date
                }
            })
    }

    private fun createImageList(recyclerView: RecyclerView, images: List<Image>) =
        recyclerView.createBindingRecyclerView(
            items = images,
            viewBindingCreator = { parent, inflater ->
                ItemImageBinding.inflate(inflater, parent, false)
            },
            prepareHolder = { item, viewBinding, _ ->
                viewBinding.item = item
                viewBinding.root.setOnClickListener {
                    viewModel.interact(ImageInteraction.OpenDetails(item))
                }
            }
        )
}