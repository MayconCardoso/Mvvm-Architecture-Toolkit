package com.mctech.architecture.mvvm.x.core

/**
 * Used to manage separated component lifecycle. For example, let's say we have 3 different components on your screen.
 * Each component should load some individual data and show on the screen.
 *
 * So basically, you can start three Coroutines loading data flow and update each livedata with this component.
 * And according the requests return some data or error you just update this component that your screen component will be notified with the changes and so on.
 *
 */
sealed class ComponentState<out T> {

    /**
     * Let's say the user open the screen at the first time. The component will be initializing, right?
     * But, what about if the user change the screen orientation? The view lifecycle will call the 'onCreate' method again.
     * In this case, your component is not initializing anymore, so you do not need to load your data again.
     */
    object Initializing : ComponentState<Nothing>()

    /**
     * This is the loading state of your component.
     */
    sealed class Loading : ComponentState<Nothing>(){
        /**
         * Let's say you have a list component.
         * When you are loading from empty you wanna show a 'Screen loading progress'
         */
        object FromEmpty : Loading()

        /**
         * Let's say you have a list component.
         * When you are loading from a not empty state you wanna show only a small loading progress at the bottom of your screen.
         */
        object FromData  : Loading()
    }

    /**
     * When same error happen on your component.
     */
    data class Error(val reason: Throwable) : ComponentState<Nothing>()

    /**
     * When everything is ok and loaded on your component.
     */
    data class Success<T>(val result: T) : ComponentState<T>()
}