package com.mctech.architecture.mvvm.core.databinding

import android.view.View
import android.view.View.*
import androidx.databinding.BindingAdapter
import com.mctech.architecture.mvvm.core.ComponentState
import com.mctech.library.view.ktx.animateShowByState

@BindingAdapter("showOnLoading", requireAll = false)
fun View.showOnLoading(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading,
            animate = animate
        )
    }
}

@BindingAdapter("showOnLoadingFromEmpty", requireAll = false)
fun View.showOnLoadingFromEmpty(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromEmpty,
            animate = animate
        )
    }
}

@BindingAdapter("showOnLoadingFromData", requireAll = false)
fun View.showOnLoadingFromData(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromData,
            animate = animate
        )
    }
}

@BindingAdapter("showOnLoadingOrInitializing", requireAll = false)
fun View.showOnLoadingOrInitializing(state: ComponentState<*>?, animate : Boolean = false) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading || state is ComponentState.Initializing,
            animate = animate
        )
    }
}

@BindingAdapter("showOnSuccess", requireAll = false)
fun View.showOnSuccess(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success,
            animate = animate
        )
    }
}

@BindingAdapter("showOnSuccessOrWithData", requireAll = false)
fun View.showOnSuccessOrWithData(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success || state is ComponentState.Loading.FromData,
            animate = animate
        )
    }
}

@BindingAdapter("showOnError", requireAll = false)
fun View.showOnError(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Error,
            animate = animate
        )
    }
}

@BindingAdapter("hideOnError", requireAll = false)
fun View.hideOnError(state: ComponentState<*>?, animate : Boolean = true) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state !is ComponentState.Error,
            animate = animate
        )
    }
}

@BindingAdapter("showByCondition", requireAll = false)
fun View.showByCondition(state: Boolean, animate : Boolean = true) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = animate
    )
}

@BindingAdapter("showByConditionOrInvisible", requireAll = false)
fun View.showByConditionOrInvisible(state: Boolean, animate : Boolean = true) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = animate,
        hideState = INVISIBLE
    )
}

private fun showViewByState(view : View, isToShow : Boolean, animate : Boolean, hideState : Int = GONE){
    if(animate){
        view.animateShowByState(isToShow)
    }
    else{
        view.visibility = if (isToShow)
            VISIBLE
        else
            hideState
    }
}