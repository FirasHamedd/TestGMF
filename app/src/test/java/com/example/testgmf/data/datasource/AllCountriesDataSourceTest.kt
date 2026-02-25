package com.example.testgmf.data.datasource

import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.shared.data.ApiService
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AllCountriesDataSourceTest {

    private val apiService: ApiService = mockk()
    private lateinit var allCountriesDataSource: AllCountriesDataSource

    @BeforeEach
    fun setUp() {
        allCountriesDataSource = AllCountriesDataSource(apiService)
    }

    @Test
    fun `Given service returns Success - When fetchAllCountries is called - Then should return Success`() =
        runTest {
            // Given
            val successResponse = mockk<List<CountryDataModel>>()
            coEvery {
                apiService.fetchAllCountries()
            } coAnswers {
                Response.success(successResponse)
            }

            // When
            val response: Result<List<CountryDataModel>, Error> =
                allCountriesDataSource.fetchAllCountries()

            // Then
            assertTrue(actual = response is Result.Success)
            assertEquals(expected = successResponse, actual = response.value)
            coVerify(exactly = 1) {
                apiService.fetchAllCountries()
            }
        }

    @Test
    fun `Given service returns Failure - When fetchAllCountries is called - Then should return Failure`() =
        runTest {
            // Given
            coEvery {
                apiService.fetchAllCountries()
            } coAnswers {
                Response.error<List<CountryDataModel>>(
                    400,
                    "{}".toResponseBody(
                        contentType = "*/*".toMediaType(),
                    ),
                )
            }

            // When
            val response: Result<List<CountryDataModel>, Error> =
                allCountriesDataSource.fetchAllCountries()

            // Then
            assertTrue(actual = response is Result.Failure)
            assertEquals(expected = Error.UNKNOWN, actual = response.error)
            coVerify(exactly = 1) {
                apiService.fetchAllCountries()
            }
        }
}
