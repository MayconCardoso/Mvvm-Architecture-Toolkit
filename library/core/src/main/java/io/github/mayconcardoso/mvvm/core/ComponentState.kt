package io.github.mayconcardoso.mvvm.core

/**
 * Used to manage separated component lifecycle. For example, let's say we have 3 different components on your screen.
 * Each component should load some individual data and show on the screen.
 *
 * So basically, you can start three Coroutines loading data flow and update each Livedata with this component.
 * And according the requests return some data or error you just update this component that your screen component will be notified with the changes and so on.
 *
 */
sealed class ComponentState<out T> {
    /**
     * This is the loading state of your component.
     */
    sealed class Loading : ComponentState<Nothing>() {
        /**
         * Let's say you have a list component.
         * When you are loading from empty you wanna show a 'Screen loading progress'
         */
        object FromEmpty : Loading()

        /**
         * Let's say you have a list component.
         * When you are loading from a not empty state you wanna show only a small loading progress at the bottom of your screen.
         */
        data class FromState<T>(val previousState: ComponentState<T>) : Loading()
    }

    /**
     * When same error happen on your component.
     */
    data class Error<T>(val reason: Throwable, val lastData: T? = null) : ComponentState<T>()

    /**
     * When everything is ok and loaded on your component.
     */
    data class Success<T>(val result: T) : ComponentState<T>()
}
