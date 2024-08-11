package com.saal.domain.common.error

sealed class Failure {
    data class DataError(val message: String?) : Failure()
    data class GenericError(val message: String?) : Failure()
}