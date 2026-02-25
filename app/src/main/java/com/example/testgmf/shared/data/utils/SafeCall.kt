package com.example.testgmf.shared.data.utils

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.domain.Error

@Throws(CancellationException::class)
suspend inline fun <reified D> safeCall(
    execute: () -> Response<D>,
    noinline customSuccessHandler: ((Response<D>) -> Result<D, Error>)? = null,
    noinline customFailureHandler: ((Response<D>) -> Result<D, Error>)? = null,
): Result<D, Error> {
    val response: Response<D> = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Failure(error = Error.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Failure(error = Error.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        e.printStackTrace()
        return Result.Failure(error = Error.UNKNOWN)
    }
    return if (response.isSuccessful) {
        customSuccessHandler?.let {
            it(response)
        } ?: run {
            responseToResult(response)
        }
    } else {
        customFailureHandler?.let {
            it(response)
        } ?: run {
            responseToResult(response)
        }
    }
}
