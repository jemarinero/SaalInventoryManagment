package com.saal.domain.common.result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<out T, out E> {

    data class Success<out T>(val data: T): Result<T, Nothing>()

    data class Error<out E>(val error: E): Result<Nothing, E>()
}

fun <T> resultSuccess(data: T) = Result.Success(data)
fun <T> resultError(error: T) = Result.Error(error)

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    contract { callsInPlace(action, kotlin.contracts.InvocationKind.AT_MOST_ONCE) }
    if (this is Result.Success) action(data)
    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T, E> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    contract { callsInPlace(action, kotlin.contracts.InvocationKind.AT_MOST_ONCE) }
    if (this is Result.Error) action(error)
    return this
}