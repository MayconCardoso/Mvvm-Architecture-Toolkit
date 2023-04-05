package io.github.mayconcardoso.mvvm.core.ktx

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner

fun Context.getLifeCycleOwner(): LifecycleOwner {
  if (this is LifecycleOwner) {
    return this
  }

  if (this is android.view.ContextThemeWrapper) {
    return this.baseContext.getLifeCycleOwner()
  }

  if (this is androidx.appcompat.view.ContextThemeWrapper) {
    return this.baseContext.getLifeCycleOwner()
  }

  throw IllegalArgumentException("The provided context is not a LifecycleOwner")
}

fun Context.getActivity(): AppCompatActivity {
  if (this is AppCompatActivity) {
    return this
  }

  if (this is android.view.ContextThemeWrapper) {
    return this.baseContext.getActivity()
  }

  if (this is androidx.appcompat.view.ContextThemeWrapper) {
    return this.baseContext.getActivity()
  }

  throw IllegalArgumentException("The provided context is not a Activity")
}