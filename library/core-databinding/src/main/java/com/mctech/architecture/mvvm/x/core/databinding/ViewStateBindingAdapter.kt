package com.mctech.architecture.mvvm.x.core.databinding

import android.view.View
import android.view.View.*
import androidx.databinding.BindingAdapter
import com.mctech.architecture.mvvm.x.core.ComponentState
import com.mctech.library.view.ktx.animateShowByState

@BindingAdapter("showOnLoading", requireAll = false)
fun View.showOnLoading(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading,
            animate = false
        )
    }
}

@BindingAdapter("showOnLoadingFromEmpty", requireAll = false)
fun View.showOnLoadingFromEmpty(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromEmpty,
            animate = false
        )
    }
}

@BindingAdapter("showOnLoadingFromData", requireAll = false)
fun View.showOnLoadingFromData(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromData,
            animate = false
        )
    }
}

@BindingAdapter("showOnLoadingOrInitializing", requireAll = false)
fun View.showOnLoadingOrInitializing(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading || state is ComponentState.Initializing,
            animate = false
        )
    }
}

@BindingAdapter("showOnSuccess", requireAll = false)
fun View.showOnSuccess(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success,
            animate = false
        )
    }
}

@BindingAdapter("showOnSuccessOrWithData", requireAll = false)
fun View.showOnSuccessOrWithData(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success || state is ComponentState.Loading.FromData,
            animate = false
        )
    }
}


@BindingAdapter("showOnSuccessWithEmptyData", requireAll = false)
fun View.showOnSuccessWithEmptyData(state: ComponentState<List<*>>?) {
    state?.also {
        val shouldShow = when(state){
            is ComponentState.Success -> {
                state.result.isEmpty()
            }
            else -> false
        }

        showViewByState(
            view = this,
            isToShow = shouldShow,
            animate = false
        )
    }
}

@BindingAdapter("showOnSuccessWithNotEmptyData", requireAll = false)
fun View.showOnSuccessWithNotEmptyData(state: ComponentState<List<*>>?) {
    state?.also {
        val shouldShow = when(state){
            is ComponentState.Loading.FromData -> true
            is ComponentState.Success -> {
                state.result.isNotEmpty()
            }
            else -> false
        }

        showViewByState(
            view = this,
            isToShow = shouldShow,
            animate = false
        )
    }
}

@BindingAdapter("showOnError", requireAll = false)
fun View.showOnError(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Error,
            animate = false
        )
    }
}

@BindingAdapter("hideOnError", requireAll = false)
fun View.hideOnError(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state !is ComponentState.Error,
            animate = false
        )
    }
}

@BindingAdapter("showByCondition", requireAll = false)
fun View.showByCondition(state: Boolean) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = false
    )
}

@BindingAdapter("showByConditionOrInvisible", requireAll = false)
fun View.showByConditionOrInvisible(state: Boolean) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = false,
        hideState = INVISIBLE
    )
}

@BindingAdapter("animatedShowOnLoading", requireAll = false)
fun View.animatedShowOnLoading(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnLoadingFromEmpty", requireAll = false)
fun View.animatedShowOnLoadingFromEmpty(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromEmpty,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnLoadingFromData", requireAll = false)
fun View.animatedShowOnLoadingFromData(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading.FromData,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnLoadingOrInitializing", requireAll = false)
fun View.animatedShowOnLoadingOrInitializing(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Loading || state is ComponentState.Initializing,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnSuccess", requireAll = false)
fun View.animatedShowOnSuccess(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnSuccessOrWithData", requireAll = false)
fun View.animatedShowOnSuccessOrWithData(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Success || state is ComponentState.Loading.FromData,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnSuccessWithEmptyData", requireAll = false)
fun View.animatedShowOnSuccessWithEmptyData(state: ComponentState<List<*>>?) {
    state?.also {
        val shouldShow = when(state){
            is ComponentState.Success -> {
                state.result.isEmpty()
            }
            else -> false
        }

        showViewByState(
            view = this,
            isToShow = shouldShow,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnSuccessWithNotEmptyData", requireAll = false)
fun View.animatedShowOnSuccessWithNotEmptyData(state: ComponentState<List<*>>?) {
    state?.also {
        val shouldShow = when(state){
            is ComponentState.Loading.FromData -> true
            is ComponentState.Success -> {
                state.result.isNotEmpty()
            }
            else -> false
        }

        showViewByState(
            view = this,
            isToShow = shouldShow,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowOnError", requireAll = false)
fun View.animatedShowOnError(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state is ComponentState.Error,
            animate = true
        )
    }
}

@BindingAdapter("animatedHideOnError", requireAll = false)
fun View.animatedHideOnError(state: ComponentState<*>?) {
    state?.also {
        showViewByState(
            view = this,
            isToShow = state !is ComponentState.Error,
            animate = true
        )
    }
}

@BindingAdapter("animatedShowByCondition", requireAll = false)
fun View.animatedShowByCondition(state: Boolean) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = true
    )
}

@BindingAdapter("animatedShowByConditionOrInvisible", requireAll = false)
fun View.animatedShowByConditionOrInvisible(state: Boolean) {
    showViewByState(
        view = this,
        isToShow = state,
        animate = true,
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