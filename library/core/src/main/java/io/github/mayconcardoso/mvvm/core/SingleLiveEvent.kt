package io.github.mayconcardoso.mvvm.core

import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * The original google code with some changes to make it easier to be used.
 */
class SingleLiveEvent<T> : MediatorLiveData<T>() {
    private val mPending = AtomicBoolean(false)
    private val mObservers = mutableMapOf<String, Observer<in T>>()

    fun observe(key: String, owner: LifecycleOwner, observer: Observer<in T>) {
        mObservers[key] = observer

        super.observe(owner) { t ->
            synchronized(mObservers) {
                if (mPending.compareAndSet(true, false)) {
                    mObservers.forEach {
                        it.value.onChanged(t)
                    }
                }
            }
        }

        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                mObservers.remove(key)
            }
        }

        owner.lifecycle.addObserver(lifecycleObserver)
    }

    fun removeObserver(key: String) {
        synchronized(mObservers) {
            mObservers.filter {
                it.key == key
            }.forEach {
                super.removeObserver(it.value)
            }
            mObservers.remove(key)
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}
