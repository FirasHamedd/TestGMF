package com.example.testgmf.data.datasource

import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import com.example.testgmf.shared.data.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import io.mockk.coVerify
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SingleCountryDetailsDataSourceTest {
    private val apiService: ApiService = mockk()
    private lateinit var singleCountryDetailsDataSource: SingleCountryDetailsDataSource

    @BeforeEach
    fun setUp() {
        singleCountryDetailsDataSource = SingleCountryDetailsDataSource(apiService = apiService)
    }

    @Test
    fun `Given service returns Success - When fetchSingleCountryDetails is called - Then should return Success`() =
        runTest {
            // Given
            val successResponse = mockk<SingleCountryDetailsDataModel>()
            coEvery {
                apiService.fetchSingleCountry(countryName = any())
            } coAnswers {
                Response.success(listOf(successResponse))
            }

            // When
            val response: Result<List<SingleCountryDetailsDataModel>, Error> =
                singleCountryDetailsDataSource.fetchSingleCountryDetails(countryName = "countryName")

            // Then
            assertTrue(actual = response is Result.Success)
            assertEquals(expected = successResponse, actual = response.value.first())
            coVerify(exactly = 1) {
                apiService.fetchSingleCountry(countryName = "countryName")
            }
        }

    @Test
    fun `Given service returns Failure - When fetchSingleCountryDetails is called - Then should return Failure`() =
        runTest {
            // Given
            coEvery {
                apiService.fetchSingleCountry(countryName = any())
            } coAnswers {
                Response.error<List<SingleCountryDetailsDataModel>>(
                    400,
                    "{}".toResponseBody(
                        contentType = "*/*".toMediaType(),
                    ),
                )
            }

            // When
            val response: Result<List<SingleCountryDetailsDataModel>, Error> =
                singleCountryDetailsDataSource.fetchSingleCountryDetails(countryName = "countryName")

            // Then
            assertTrue(actual = response is Result.Failure)
            assertEquals(expected = Error.UNKNOWN, actual = response.error)
            coVerify(exactly = 1) {
                apiService.fetchSingleCountry(countryName = "countryName")
            }
        }

}