package com.example.testgmf.shared.data.utils

import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SafeCallTest {
    private val testingService: TestingService = mockk()

    @Test
    fun `Given Service returns Success - When safeCall - Then return Success`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.success("success")
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Success)
        assertEquals(
            expected = "success",
            actual = response.value,
        )
    }

    @Test
    fun `Given Service returns Success with null response - When safeCall - Then return Success`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.success(null)
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNKNOWN,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns Failure - When safeCall - Then return Failure`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.error<String>(
                400,
                "{}".toResponseBody(
                    contentType = "*/*".toMediaType(),
                ),
            )
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNKNOWN,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service throws UnresolvedAddressException - When safeCall - Then return Failure NO_INTERNET`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            throw UnresolvedAddressException()
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.NO_INTERNET,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service throws SerializationException - When safeCall - Then return Failure SERIALIZATION`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            throw SerializationException()
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.SERIALIZATION,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service throws CancellationException - When safeCall - Then throw CancellationException`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            throw CancellationException("network test exception")
        }

        // When
        try {
            safeCall(
                execute = {
                    testingService.callTestingService()
                },
            )
        } catch (e: Exception) {
            // Then
            assertTrue(actual = e is CancellationException)
        }
    }

    @Test
    fun `Given Service throws Exception - When safeCall - Then return Failure UNKNOWN`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            throw Exception("network test exception")
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNKNOWN,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns Success and customSuccessHandler is Given - When safeCall - Then return Success`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.success("emptySuccess")
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
            customSuccessHandler = {
                return@safeCall Result.Success("customSuccess")
            },
        )

        // Then
        assertTrue(actual = response is Result.Success)
        assertEquals(
            expected = "customSuccess",
            actual = response.value,
        )
    }

    @Test
    fun `Given Service returns Failure and customFailureHandler is Given - When safeCall - Then return Success anyway`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.error<String>(
                400,
                "{}".toResponseBody(
                    contentType = "*/*".toMediaType(),
                ),
            )
        }

        // When
        val response: Result<String, Error> = safeCall(
            execute = {
                testingService.callTestingService()
            },
            customFailureHandler = {
                return@safeCall Result.Success(value = "customSuccess")
            },
        )

        // Then
        assertTrue(actual = response is Result.Success)
        assertEquals(
            expected = "customSuccess",
            actual = response.value,
        )
    }
}
