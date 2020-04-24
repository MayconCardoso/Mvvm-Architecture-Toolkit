package com.mctech.architecture.mvvm.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mctech.architecture.mvvm.databinding.FragmentDetailsOfImageBinding
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImageDetailsFragment : Fragment(){
    private val viewModel   : ImageViewModel by sharedViewModel()
    private var binding     : FragmentDetailsOfImageBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentDetailsOfImageBinding.inflate(inflater, container, false).let {
            binding = it
            binding?.viewModel = viewModel
            binding?.lifecycleOwner = this
            it.root
        }
    }
}