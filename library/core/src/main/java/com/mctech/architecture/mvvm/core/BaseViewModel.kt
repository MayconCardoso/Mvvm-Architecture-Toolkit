package com.mctech.architecture.mvvm.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.isAccessible

/**
 * I do not like 'Base' classes. But in this case, it helps a lot.
 * This class is basically a simple ViewModel, but it also keep the whole user flow interactions with your screen.
 */
abstract class BaseViewModel : ViewModel() {

    // region Variables

    /**
     * Control the initialization flow avoiding recreate the same flow twice.
     */
    private var isInitialized = false

    /**
     * It is gonna keep the whole user flow on your view.
     * So let's say some error happen, you could print the whole stack trace and send it to your backend error log.
     * It will help you to trace your issues on your code and make the testing process easier.
     * Records the last 30 [UserInteraction], this value may change as we get more insight about it.
     */
    @VisibleForTesting
    val userFlowInteraction = Stack<UserInteraction>()

    /**
     * It keeps the consumers for each user interaction on the flow.
     */
    private var mappedUserInteractions =
        hashMapOf<KClass<out UserInteraction>, suspend (UserInteraction) -> Unit>()

    /**
     * This is a simple observable that your view will be observing to handle commands.
     * A command is an action that ViewModel will send only once for the attached view, suck as:
     * - Open this dialog.
     * - Navigate to this screen.
     * - Navigate back from this screen.
     */
    private val _commandObservable = SingleLiveEvent<ViewCommand>()
    val commandObservable: LiveData<ViewCommand> get() = _commandObservable

    // endregion

    // region Initialization

    /**
     * Initialize mapped interactions
     */
    init {
        mapInteractionObservers()
    }

    /**
     * Call this function on `onCreate` of your Activity or Fragment.
     */
    fun initialize() {
        if (isInitialized) {
            return
        }

        viewModelScope.launch {
            initializeComponents()
        }

        isInitialized = true
    }

    /**
     * Override this function to initialize your component flow.
     * It will be called only once when the view model is created.
     */
    protected open suspend fun initializeComponents() = Unit

    /**
     * This is experimental. You should not use this code on production code.
     */
    private fun mapInteractionObservers() {
        this::class.members.forEach { member ->
            member.annotations.forEach { annotation ->
                if (annotation is OnInteraction) {

                    member.isAccessible = true

                    mappedUserInteractions[annotation.target] = {
                        if (member.parameters.size <= 1) {
                            member.callSuspend(this)
                        } else {
                            member.callSuspend(this, it)
                        }
                    }
                }
            }
        }
    }

    // endregion

    // region Lifecycle

    /**
     * Used to clean all cache when view model is destroyed.
     */
    override fun onCleared() {
        userFlowInteraction.clear()
        super.onCleared()
    }

    // endregion

    // region Interactions

    /**
     * Called by view to send 'an interaction' to the view model by using the view model scope.
     */
    fun interact(userInteraction: UserInteraction, delay: Long = 0L) {
        viewModelScope.launch {
            delay(delay)
            userFlowInteraction.add(userInteraction)
            internalInteractionHandler(userInteraction)
        }
    }

    /**
     * It is the function that is called every single interaction the screen send.
     * So you can basically override it and handle the specific interaction by using a 'when' flow for example.
     */
    private suspend fun internalInteractionHandler(interaction: UserInteraction) {
        // It is a mapped function
        if (mappedUserInteractions.containsKey(interaction::class)) {
            mappedUserInteractions[interaction::class]?.invoke(interaction)
            return
        }

        // Handle by using legacy way
        handleUserInteraction(interaction)
    }

    /**
     * It is the function that is called every single interaction the screen send.
     * So you can basically override it and handle the specific interaction by using a 'when' flow for example.
     */
    protected open suspend fun handleUserInteraction(interaction: UserInteraction) = Unit

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
            handleUserInteraction(userFlowInteraction.last())
        }
    }

    /**
     * If you need to check if there is a specific interaction on your flow.
     */
    fun <T : UserInteraction> hasInteractionOnFlow(item: T) = hasMoreInteractionOnFlowThen(item, 0)

    /**
     * If you need to check if there is a specific interaction on your flow.
     */
    fun <T : UserInteraction> hasMoreInteractionOnFlowThen(item: T, count: Int): Boolean {
        return userFlowInteraction.count {
            it.javaClass == item.javaClass
        } > count
    }

    // endregion

    /**
     * Used to send a command to your view.
     */
    protected open suspend fun sendCommand(viewCommand: ViewCommand) = withContext(Dispatchers.Main) {
        _commandObservable.value = viewCommand
    }

}
