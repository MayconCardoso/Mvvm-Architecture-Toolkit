package com.mctech.architecture.mvvm.x.core.testing.extentions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mctech.architecture.mvvm.x.core.testing.extentions.TestLiveDataScenario.Companion.testLiveDataScenario
import kotlinx.coroutines.runBlocking

@Deprecated(
    "Use testLiveDataScenario function. " +
            "Check this link for an example: " +
            "https://github.com/MayconCardoso/Mvvm-Architecture-Toolkit/tree/master/library/core-testing"
)
fun <T> LiveData<T>.testLiveData(
    scenario    : suspend () -> Unit = {},
    action      : suspend () -> Unit = {},
    assertion   : suspend (List<T>) -> Unit
) {
    testLiveDataScenario{
        whenThisScenario(scenario)
        onThisAction(action)
        assertLiveDataFlow(this@testLiveData, assertion)
    }
}

fun <T> LiveData<T>.assertIsEmpty() {
    testLiveData { it.assertEmpty() }
}

class TestLiveDataScenario {
    private val agents   = mutableListOf<ObserverAgent<*>>()
    private var action   : (suspend () -> Unit)? = null
    private var scenario : (suspend () -> Unit)? = null

    fun whenThisScenario (scenario: suspend () -> Unit) {
        if(this.scenario != null){
            throw IllegalArgumentException("You must have only one scenario by test.")
        }

        this.scenario = scenario
    }

    fun onThisAction(action: suspend () -> Unit) {
        if(this.action != null){
            throw IllegalArgumentException("You must have only one action by test.")
        }

        this.action = action
    }

    fun <T> assertLiveDataFlow(liveData : LiveData<T>, assert : suspend (List<T>) -> Unit){
        agents.add(
            ObserverAgent(liveData, assert)
        )
    }

    private fun execute(){
        try {
            runBlocking {
                // Prepare test
                scenario?.invoke()

                // Observe data
                agents.forEach {
                    it.observe()
                }

                // Call the action trigger
                action?.invoke()

                // Notify
                agents.forEach {
                    it.resume()
                }
            }
        } finally {
            agents.forEach {
                it.release()
            }
        }
    }
    companion object {
        fun testLiveDataScenario(block: TestLiveDataScenario.() -> Unit) {
            TestLiveDataScenario()
                .apply(block)
                .execute()
        }
    }
}

internal class ObserverAgent<T>(
    private val liveData : LiveData<T>,
    private val assert : suspend (List<T>) -> Unit
){
    private val emittedValues = mutableListOf<T>()
    private val observer = Observer<T> {
        emittedValues.add(it)
    }

    fun observe(){
        liveData.observeForever(observer)
    }

    suspend fun resume(){
        assert.invoke(emittedValues)
    }

    fun release(){
        liveData.removeObserver(observer)
    }
}