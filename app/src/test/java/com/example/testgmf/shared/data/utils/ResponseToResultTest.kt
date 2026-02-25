package com.example.testgmf.shared.data.utils

import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ResponseToResultTest {
    private val testingService: TestingService = mockk()

    @Test
    fun `Given Service returns Success - When responseToResult - Then return Success`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.success("success")
        }

        // When
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Success)
        assertEquals(
            expected = "success",
            actual = response.value,
        )
    }

    @Test
    fun `Given Service returns Success with null response - When responseToResult - Then return Success`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.success(null)
        }

        // When
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNKNOWN,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns code 400 - When responseToResult - Then return Failure UNKNOWN`() = runTest {
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
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNKNOWN,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns code 401 - When responseToResult - Then return Failure UNAUTHORIZED`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.error<String>(
                401,
                "{}".toResponseBody(
                    contentType = "*/*".toMediaType(),
                ),
            )
        }

        // When
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.UNAUTHORIZED,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns code 408 - When responseToResult - Then return Failure REQUEST_TIMEOUT`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.error<String>(
                408,
                "{}".toResponseBody(
                    contentType = "*/*".toMediaType(),
                ),
            )
        }

        // When
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.REQUEST_TIMEOUT,
            actual = response.error,
        )
    }

    @Test
    fun `Given Service returns code 5xx - When responseToResult - Then return Failure SERVER_ERROR`() = runTest {
        // Given
        coEvery {
            testingService.callTestingService()
        } coAnswers {
            Response.error<String>(
                500,
                "{}".toResponseBody(
                    contentType = "*/*".toMediaType(),
                ),
            )
        }

        // When
        val response: Result<String, Error> = responseToResult(
            response = testingService.callTestingService(),
        )

        // Then
        assertTrue(actual = response is Result.Failure)
        assertEquals(
            expected = Error.SERVER_ERROR,
            actual = response.error,
        )
    }
}
