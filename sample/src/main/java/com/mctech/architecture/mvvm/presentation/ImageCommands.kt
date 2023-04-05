package com.mctech.architecture.mvvm.presentation

import io.github.mayconcardoso.mvvm.core.ViewCommand

sealed class ImageCommands : ViewCommand {
  object OpenImageDetails : ImageCommands()
}