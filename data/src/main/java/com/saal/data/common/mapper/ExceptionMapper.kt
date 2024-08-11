package com.saal.data.common.mapper

import com.saal.data.common.exception.DatabaseException
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.resultError

fun Exception.toFailure() = when(this) {
    is DatabaseException -> resultError(Failure.DataError(message))
    else -> resultError(Failure.GenericError(message))
}