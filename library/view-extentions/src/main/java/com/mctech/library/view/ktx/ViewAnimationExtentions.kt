package com.mctech.library.view.ktx

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.mctech.library.view.ktx.defaults.AnimationListener

fun View.animateShowByState(shouldShow: Boolean, time: Long = 200): Animation? {
    if (isSameState(shouldShow))
        return null

    return if (shouldShow) animateShow(time)
    else animateHide(time)
}

fun View.animateHide(time: Long = 200): Animation {
    val animation = AlphaAnimation(1F, 0F).apply {
        duration = time
        interpolator = AccelerateInterpolator()
        fillAfter = true
        setAnimationListener(object : AnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.GONE
            }
        })
    }

    startAnimation(animation)
    return animation
}

fun View.animateShow(time: Long = 200): Animation {
    val animation = AlphaAnimation(0F, 1F).apply {
        duration = time
        interpolator = AccelerateInterpolator()
        fillAfter = true
        setAnimationListener(object : AnimationListener() {
            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
                clearAnimation()
            }
        })
    }

    startAnimation(animation)
    return animation
}

private fun View.isSameState(showView: Boolean): Boolean {
    if (visibility == View.VISIBLE && showView)
        return true

    if (visibility == View.GONE && !showView)
        return true

    return false
}