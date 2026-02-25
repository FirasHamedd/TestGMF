package com.example.testgmf.shared.data.utils

import retrofit2.Response
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.domain.Error

inline fun <reified T> responseToResult(response: Response<T>): Result<T, Error> = when (response.code()) {
    in 200..299 -> response.body()?.let { safeResponseBody ->
        Result.Success(value = safeResponseBody)
    } ?: Result.Failure(Error.UNKNOWN)
    401 -> Result.Failure(error = Error.UNAUTHORIZED)
    408 -> Result.Failure(error = Error.REQUEST_TIMEOUT)
    in 500..599 -> Result.Failure(error = Error.SERVER_ERROR)
    else -> Result.Failure(error = Error.UNKNOWN)
}
