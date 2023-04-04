package com.mctech.architecture.mvvm.x.core.testing.extentions

import org.assertj.core.api.Assertions

fun <T> List<T>.assertEmpty() = assertCount(0)
fun <T> List<T>.assertFirst() = assertAtPosition(0)
fun <T> List<T>.assertSecond() = assertAtPosition(1)
fun <T> List<T>.assertLast() = assertAtPosition(size - 1)

fun <T> List<T>.assertCount(count: Int) = Assertions.assertThat(size).isEqualTo(count)
fun <T> List<T>.assertAtPosition(position: Int) = Assertions.assertThat(get(position))

fun Any.assertThat() = Assertions.assertThat(this)