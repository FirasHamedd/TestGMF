package com.example.testgmf.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.testgmf.R
import com.example.testgmf.presentation.state.SingleCountryDetailsUIState
import com.example.testgmf.presentation.ui.theme.Black
import com.example.testgmf.presentation.ui.theme.LightGrey
import com.example.testgmf.presentation.ui.theme.Spacing
import com.example.testgmf.presentation.viewmodel.details.SingleCountryDetailsViewModel
import com.example.testgmf.presentation.viewmodel.details.SingleCountryDetailsViewModelAction
import com.example.testgmf.presentation.viewmodel.details.SingleCountryDetailsViewModelEvents
import com.example.testgmf.shared.presentation.utils.LifeCycleViewObserver
import com.example.testgmf.shared.presentation.utils.ObserveAsEvents
import com.example.testgmf.shared.presentation.view.CommonGeneralError
import com.example.testgmf.shared.presentation.view.Loader
import com.example.testgmf.shared.presentation.view.ScreenContent

@Composable
fun SingleCountryScreenRoot(
    singleCountryDetailsViewModel: SingleCountryDetailsViewModel = hiltViewModel(),
    countryName: String,
    onBackClicked: () -> (Unit),
) {
    val uiState by singleCountryDetailsViewModel.uiState.collectAsStateWithLifecycle()

    val isGeneralCommonErrorDialogVisible = remember { mutableStateOf(value = false) }

    ObserveAsEvents(flow = singleCountryDetailsViewModel.events) { events ->
        when (events) {
            is SingleCountryDetailsViewModelEvents.ShowErrorDialog -> {
                isGeneralCommonErrorDialogVisible.value = true
            }
        }
    }

    LifeCycleViewObserver(
        onCreate = {
            singleCountryDetailsViewModel.onAction(
                action = SingleCountryDetailsViewModelAction.OnCountryDetailsScreenDisplayed(
                    countryName = countryName,
                ),
            )
        },
    )

    SingleCountryScreen(
        uiState = uiState,
        onBackClicked = onBackClicked,
    )

    CommonGeneralError(
        shouldShowDialogState = isGeneralCommonErrorDialogVisible,
        onRetryButtonClicked = {
            singleCountryDetailsViewModel.onAction(
                action = SingleCountryDetailsViewModelAction.OnErrorDialogRetryButtonClicked(
                    countryName = countryName,
                ),
            )
        }
    )
}

@Composable
private fun SingleCountryScreen(
    uiState: SingleCountryDetailsUIState,
    onBackClicked: () -> (Unit),
    modifier: Modifier = Modifier,
) {
    ScreenContent(
        topBarTitle = uiState.countryDetails.name,
        onBackClicked = onBackClicked,
        modifier = modifier
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(space = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = LightGrey)
                .padding(paddingValues = paddingValues)
                .padding(all = Spacing.md),
        ) {
            AsyncImage(
                model = uiState.countryDetails.flag,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(height = 200.dp, width = 300.dp),
            )

            Text(
                text = stringResource(
                    id = R.string.country_name,
                    uiState.countryDetails.name
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(
                    id = R.string.country_capital,
                    uiState.countryDetails.capital
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(
                    id = R.string.country_continent,
                    uiState.countryDetails.continent
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(
                    id = R.string.country_population,
                    uiState.countryDetails.population
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(
                    id = if (uiState.countryDetails.independent) {
                        R.string.country_independent
                    } else {
                        R.string.country_not_independent
                    }
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(
                    id = if (uiState.countryDetails.landlocked) {
                        R.string.country_landlocked
                    } else {
                        R.string.country_not_landlocked
                    }
                ),
                style = TextStyle(
                    color = Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(weight = 500),
                    textAlign = TextAlign.Start,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        if (uiState.isLoading) {
            Loader()
        }
    }
}