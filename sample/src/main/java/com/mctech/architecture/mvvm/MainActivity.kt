package com.mctech.architecture.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mctech.architecture.mvvm.presentation.ImageCommands
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import com.mctech.architecture.mvvm.presentation.view.ImageDetailsFragment
import com.mctech.architecture.mvvm.presentation.view.ImageListFragment
import com.mctech.architecture.mvvm.core.ViewCommand
import com.mctech.architecture.mvvm.core.ktx.bindCommand
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  /**
   * Holds the feature view model
   */
  private val viewModel by viewModels<ImageViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Load List Fragment - Just for sample purpose.
    // On a real project it could be replaced by a navigation logic.
    showDetailFragment()

    // Observe commands
    bindCommand(viewModel, ::consumeCommand)
  }

  private fun consumeCommand(command: ViewCommand) {
    when (command) {
      is ImageCommands.OpenImageDetails -> {
        openDetailScreen()
      }
    }
  }

  private fun showDetailFragment() {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.containerFragment, ImageListFragment())
      .commit()
  }

  private fun openDetailScreen() {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.containerFragment, ImageDetailsFragment())
      .commit()
  }
}
