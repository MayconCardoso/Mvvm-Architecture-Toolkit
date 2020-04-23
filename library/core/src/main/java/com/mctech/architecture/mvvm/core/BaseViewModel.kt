package com.mctech.architecture.mvvm.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * I do not like 'Base' classes. But in this case, it helps a lot.
 * This class is basically a simple ViewModel, but it also keep the whole user flow interactions with your screen.
 *
 */
abstract class BaseViewModel : ViewModel() {
    /**
     * It is gonna keep the whole user flow on your view.
     * So let's say some error happen, you could print the whole stack trace and send it to your backend error log.
     * It will help you to trace your issues on your code and make the testing process easier.
     */
    private val userFlowInteraction = Stack<UserInteraction>()

    /**
     * This is only a simple observable that your view will be observing to handle some command.
     */
    private val _commandObservable = SingleLiveEvent<ViewCommand>()
    val commandObservable : LiveData<ViewCommand> get() = _commandObservable

    /**
     * Called by view to send 'an interaction' to the view model by using the view model scope.
     */
    fun interact(userInteraction: UserInteraction) {
        viewModelScope.launch {
            suspendedInteraction(userInteraction)
        }
    }

    /**
     * Called by view to send 'a suspended interaction' to the view model.
     * It's called when the suspended function must be connect with the view life cycle.
     */
    suspend fun suspendedInteraction(userInteraction: UserInteraction) {
        userFlowInteraction.push(userInteraction)
        handleUserInteraction(userInteraction)
    }

    /**
     * It is the function that is called every single interaction the screen send.
     * So you can basically override it and handle the specific interaction by using a 'when' flow for example.
     */
    protected open suspend fun handleUserInteraction(interaction: UserInteraction) = Unit

    /**
     * Used to send a command to your view.
     */
    protected open suspend fun sendCommand(viewCommand: ViewCommand){
        _commandObservable.value = viewCommand
    }

    /**
     * Let's say the user has filled in your login form. When the 'Sign in' button is pressed
     * your view will send a 'TryLoginInteraction(user, password)' to your view model handle it.
     *
     * But some error happen and your view will receive a error state. But you wanna try to sign in again.
     * So, instead of create another interaction and send to the view model. You could just call this method
     * and it will make sure to call the last interaction you've tried to send.
     */
    fun reprocessLastInteraction() {
        viewModelScope.launch {
            suspendedReprocessLastInteraction()
        }
    }

    suspend fun suspendedReprocessLastInteraction() {
        handleUserInteraction(userFlowInteraction.last())
    }

    /**
     * If you need to check if there is a specific interaction on your flow.
     */
    fun <T : UserInteraction> hasInteractionOnFlow(item : T) : Boolean{
        return userFlowInteraction.firstOrNull() {
            it.javaClass == item.javaClass
        } != null
    }

    /**
     * If you need to check if there is a specific interaction on your flow.
     */
    fun <T : UserInteraction> hasMoreInteractionOnFlowThen(item : T, count : Int) : Boolean{
        return userFlowInteraction.count {
            it.javaClass == item.javaClass
        } > count
    }

    override fun onCleared() {
        userFlowInteraction.clear()
        super.onCleared()
    }
}