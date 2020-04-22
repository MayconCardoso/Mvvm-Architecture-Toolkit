package com.mctech.library.view.ktx

import android.util.TypedValue
import android.view.View

fun View.isHide() = this.visibility != View.VISIBLE

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisibilityByState(visible: Boolean) {
    if (visible) show()
    else hide()
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enableByState(enabled: Boolean) {
    if (enabled) enable()
    else disable()
}

fun View.dpToPixel(dip: Float) = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dip,
        context.resources.displayMetrics
)