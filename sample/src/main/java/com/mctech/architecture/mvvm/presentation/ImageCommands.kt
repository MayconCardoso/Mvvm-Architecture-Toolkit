package com.mctech.architecture.mvvm.presentation

import com.mctech.architecture.mvvm.core.ViewCommand

sealed class ImageCommands : ViewCommand {
  object OpenImageDetails : ImageCommands()
}