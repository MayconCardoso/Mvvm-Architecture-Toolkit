package com.mctech.architecture.mvvm.x.core

import kotlin.reflect.KClass

/**
 * An interaction is some user's 'intention' to do something. For example:
 *  - Load this list for me
 *  - Try sign in with these credentials
 *  - Etc.
 */
interface UserInteraction

/**
 * You can annotate your function with this policy to make it be called at runtime.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnInteraction(val target : KClass<out UserInteraction>)