package com.example.testgmf.presentation.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testgmf.domain.usecase.GetSingleCountryDetailsUseCase
import com.example.testgmf.presentation.mapper.SingleCountryDetailsUIMapper
import com.example.testgmf.presentation.state.SingleCountryDetailsUIState
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
class SingleCountryDetailsViewModel @Inject constructor(
    private val getSingleCountryDetailsUseCase: GetSingleCountryDetailsUseCase,
    private val singleCountryDetailsUIMapper: SingleCountryDetailsUIMapper,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {

    private val _events = MutableSharedFlow<SingleCountryDetailsViewModelEvents>()
    val events = _events.asSharedFlow()

    private val _uiState = MutableStateFlow(value = SingleCountryDetailsUIState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: SingleCountryDetailsViewModelAction) {
        when (action) {
            is SingleCountryDetailsViewModelAction.OnCountryDetailsScreenDisplayed -> getSingleCountryDetails(
                countryName = action.countryName,
            )
        }
    }

    private fun getSingleCountryDetails(countryName: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(dispatchersProvider.io) {
            val result = getSingleCountryDetailsUseCase.getSingleCountryDetails(
                countryName = countryName,
            )
            when (result) {
                is Result.Failure -> {
                    _events.emit(value = SingleCountryDetailsViewModelEvents.ShowErrorDialog)
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }

                is Result.Success -> {
                    val countryDetailsState = singleCountryDetailsUIMapper.mapDomainToUI(
                        singleCountryDetailsDomainModel = result.value,
                    )
                    _uiState.update { countryDetailsState }
                }
            }
        }
    }
}