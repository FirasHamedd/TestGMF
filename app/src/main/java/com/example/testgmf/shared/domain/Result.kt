package com.example.testgmf.shared.domain

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(
        val value: D,
    ) : Result<D, Nothing>

    data class Failure<out E : Error>(
        val error: Error,
    ) : Result<Nothing, E>
}

inline fun <D, E : Error, R> Result<D, E>.map(map: (D) -> R): Result<R, E> = when (this) {
    is Result.Success -> Result.Success(value = map(value))
    is Result.Failure -> Result.Failure(error = error)
}
