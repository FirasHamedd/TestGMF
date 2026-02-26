package com.example.testgmf.presentation.viewmodel.allcountries

import app.cash.turbine.test
import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.domain.usecase.GetAllCountriesUseCase
import com.example.testgmf.presentation.mapper.AllCountriesUIMapper
import com.example.testgmf.presentation.state.CountryUIState
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.testing.DispatchersProviderImplTest
import com.example.testgmf.shared.testing.MainDispatcherTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class AllCountriesViewModelTest : MainDispatcherTest {

    private val getAllCountriesUseCase: GetAllCountriesUseCase = mockk()
    private val allCountriesUIMapper: AllCountriesUIMapper = mockk()
    override lateinit var dispatchersProvider: DispatchersProviderImplTest
    private lateinit var allCountriesViewModel: AllCountriesViewModel

    @BeforeEach
    fun setUp() {
        allCountriesViewModel = AllCountriesViewModel(
            getAllCountriesUseCase = getAllCountriesUseCase,
            allCountriesUIMapper = allCountriesUIMapper,
            dispatchersProvider = dispatchersProvider,
        )
    }

    @Test
    fun `Given action is OnAllCountriesScreenDisplayed and useCase returns success - When onAction is called - Then should update state`() =
        runTest {
            // Given
            val givenCountries = listOf(mockk<CountryDomainModel>())
            val expectedCountriesState = listOf(mockk<CountryUIState>())
            coEvery {
                getAllCountriesUseCase.getAllCountries()
            } returns Result.Success(value = givenCountries)
            every {
                allCountriesUIMapper.mapDomainToUI(allCountriesDomainModel = any())
            } returns expectedCountriesState

            allCountriesViewModel.uiState.test {
                val initialState = awaitItem()
                assertFalse(actual = initialState.isLoading)
                assertTrue(actual = initialState.countries.isEmpty())

                // When
                allCountriesViewModel.onAction(
                    action = AllCountriesViewModelAction.OnAllCountriesScreenDisplayed,
                )

                val loadingState = awaitItem()
                assertTrue(actual = loadingState.isLoading)
                assertTrue(actual = loadingState.countries.isEmpty())

                val finalState = awaitItem()
                assertFalse(actual = finalState.isLoading)
                assertEquals(
                    expected = expectedCountriesState,
                    actual = finalState.countries,
                )
                coVerify(exactly = 1) {
                    getAllCountriesUseCase.getAllCountries()
                    allCountriesUIMapper.mapDomainToUI(allCountriesDomainModel = givenCountries)
                }
            }
        }

    @Test
    fun `Given action is OnAllCountriesScreenDisplayed and useCase returns failure - When onAction is called - Then should update state`() =
        runTest {
            // Given
            coEvery {
                getAllCountriesUseCase.getAllCountries()
            } returns Result.Failure(error = Error.UNKNOWN)

            allCountriesViewModel.uiState.test {
                val initialState = awaitItem()
                assertFalse(actual = initialState.isLoading)
                assertTrue(actual = initialState.countries.isEmpty())

                // When
                allCountriesViewModel.onAction(
                    action = AllCountriesViewModelAction.OnAllCountriesScreenDisplayed,
                )

                // Then
                allCountriesViewModel.events.test {
                    assertTrue(actual = awaitItem() is AllCountriesViewModelEvents.ShowErrorDialog)
                }
                val loadingState = awaitItem()
                assertTrue(actual = loadingState.isLoading)
                assertTrue(actual = loadingState.countries.isEmpty())

                val finalState = awaitItem()
                assertFalse(actual = finalState.isLoading)
                assertTrue(actual = finalState.countries.isEmpty())

                coVerify(exactly = 1) {
                    getAllCountriesUseCase.getAllCountries()
                }
                verify(exactly = 0) {
                    allCountriesUIMapper.mapDomainToUI(allCountriesDomainModel = any())
                }
            }
        }

    @Test
    fun `Given action is OnCountryClicked - When onAction is called - Then should send event`() =
        runTest {
            allCountriesViewModel.events.test {
                // When
                allCountriesViewModel.onAction(
                    action = AllCountriesViewModelAction.OnCountryClicked(countryName = "countryName"),
                )

                // Then
                assertTrue(actual = awaitItem() is AllCountriesViewModelEvents.NavigateToCountryDetails)
            }
        }
}