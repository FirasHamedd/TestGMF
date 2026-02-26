package com.example.testgmf.presentation.viewmodel.details

import app.cash.turbine.test
import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.domain.usecase.GetSingleCountryDetailsUseCase
import com.example.testgmf.presentation.mapper.SingleCountryDetailsUIMapper
import com.example.testgmf.presentation.state.SingleCountryDetailsUIState
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
class SingleCountryDetailsViewModelTest : MainDispatcherTest {

    private val getSingleCountryDetailsUseCase: GetSingleCountryDetailsUseCase = mockk()
    private val singleCountryDetailsUIMapper: SingleCountryDetailsUIMapper = mockk()
    override lateinit var dispatchersProvider: DispatchersProviderImplTest
    private lateinit var singleCountryDetailsViewModel: SingleCountryDetailsViewModel

    @BeforeEach
    fun setUp() {
        singleCountryDetailsViewModel = SingleCountryDetailsViewModel(
            getSingleCountryDetailsUseCase = getSingleCountryDetailsUseCase,
            singleCountryDetailsUIMapper = singleCountryDetailsUIMapper,
            dispatchersProvider = dispatchersProvider,
        )
    }

    @Test
    fun `Given action is OnCountryDetailsScreenDisplayed and useCase returns success - When onAction is called - Then should update state`() =
        runTest {
            // Given
            val givenCountryDomainModel = mockk<SingleCountryDetailsDomainModel>()
            val expectedCountryState = mockk<SingleCountryDetailsUIState>()
            every { expectedCountryState.isLoading } returns false
            coEvery {
                getSingleCountryDetailsUseCase.getSingleCountryDetails(countryName = any())
            } returns Result.Success(value = givenCountryDomainModel)
            every {
                singleCountryDetailsUIMapper.mapDomainToUI(singleCountryDetailsDomainModel = any())
            } returns expectedCountryState

            singleCountryDetailsViewModel.uiState.test {
                val initialState = awaitItem()
                assertFalse(actual = initialState.isLoading)

                // When
                singleCountryDetailsViewModel.onAction(
                    action = SingleCountryDetailsViewModelAction.OnCountryDetailsScreenDisplayed(
                        countryName = "countryName",
                    ),
                )

                val loadingState = awaitItem()
                assertTrue(actual = loadingState.isLoading)

                val finalState = awaitItem()
                assertFalse(actual = finalState.isLoading)
                assertEquals(
                    expected = expectedCountryState,
                    actual = finalState,
                )
                coVerify(exactly = 1) {
                    getSingleCountryDetailsUseCase.getSingleCountryDetails(countryName = "countryName")
                    singleCountryDetailsUIMapper.mapDomainToUI(singleCountryDetailsDomainModel = givenCountryDomainModel)
                }
            }
        }

    @Test
    fun `Given action is OnCountryDetailsScreenDisplayed and useCase returns failure - When onAction is called - Then should update state`() =
        runTest {
            // Given
            coEvery {
                getSingleCountryDetailsUseCase.getSingleCountryDetails(countryName = any())
            } returns Result.Failure(error = Error.UNKNOWN)

            singleCountryDetailsViewModel.uiState.test {
                val initialState = awaitItem()
                assertFalse(actual = initialState.isLoading)

                // When
                singleCountryDetailsViewModel.onAction(
                    action = SingleCountryDetailsViewModelAction.OnCountryDetailsScreenDisplayed(
                        countryName = "countryName",
                    ),
                )

                // Then
                singleCountryDetailsViewModel.events.test {
                    assertTrue(actual = awaitItem() is SingleCountryDetailsViewModelEvents.ShowErrorDialog)
                }
                val loadingState = awaitItem()
                assertTrue(actual = loadingState.isLoading)

                val finalState = awaitItem()
                assertFalse(actual = finalState.isLoading)

                coVerify(exactly = 1) {
                    getSingleCountryDetailsUseCase.getSingleCountryDetails(countryName = "countryName")
                }
                verify(exactly = 0) {
                    singleCountryDetailsUIMapper.mapDomainToUI(singleCountryDetailsDomainModel = any())
                }
            }
        }
}