package com.mctech.library.view.ktx.defaults

import android.view.animation.Animation

abstract class AnimationListener : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) = Unit
    override fun onAnimationEnd(animation: Animation?) = Unit
    override fun onAnimationStart(animation: Animation?) = Unit
}