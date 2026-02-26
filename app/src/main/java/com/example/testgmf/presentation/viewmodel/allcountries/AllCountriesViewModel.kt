package com.example.testgmf.presentation.viewmodel.allcountries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testgmf.domain.usecase.GetAllCountriesUseCase
import com.example.testgmf.presentation.mapper.AllCountriesUIMapper
import com.example.testgmf.presentation.state.AllCountriesUIState
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.domain.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCountriesViewModel @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val allCountriesUIMapper: AllCountriesUIMapper,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {

    private val _events = MutableSharedFlow<AllCountriesViewModelEvents>()
    val events = _events.asSharedFlow()

    private val _uiState = MutableStateFlow(value = AllCountriesUIState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AllCountriesViewModelAction) {
        when (action) {
            is AllCountriesViewModelAction.OnAllCountriesScreenDisplayed -> getAllCountries()
            is AllCountriesViewModelAction.OnCountryClicked -> onCountryClicked(countryName = action.countryName)
        }
    }

    private fun getAllCountries() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(dispatchersProvider.io) {
            when (val result = getAllCountriesUseCase.getAllCountries()) {
                is Result.Failure -> {
                    _events.emit(value = AllCountriesViewModelEvents.ShowErrorDialog)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }

                is Result.Success -> {
                    val countries = allCountriesUIMapper.mapDomainToUI(
                        allCountriesDomainModel = result.value,
                    )
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            countries = countries,
                        )
                    }
                }
            }
        }
    }

    private fun onCountryClicked(countryName: String) {
        viewModelScope.launch {
            _events.emit(
                value = AllCountriesViewModelEvents.NavigateToCountryDetails(
                    countryName = countryName,
                ),
            )
        }
    }
}