package com.example.testgmf.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.testgmf.presentation.state.AllCountriesUIState
import com.example.testgmf.presentation.state.CountryUIState
import com.example.testgmf.presentation.viewmodel.allcountries.AllCountriesViewModel
import com.example.testgmf.presentation.viewmodel.allcountries.AllCountriesViewModelAction
import com.example.testgmf.shared.presentation.view.Loader
import com.example.testgmf.shared.presentation.view.ScreenContent
import com.example.testgmf.presentation.ui.theme.Black
import com.example.testgmf.presentation.ui.theme.CardBackground
import com.example.testgmf.presentation.ui.theme.Grey
import com.example.testgmf.presentation.ui.theme.Rounding
import com.example.testgmf.presentation.ui.theme.Spacing
import com.example.testgmf.presentation.viewmodel.allcountries.AllCountriesViewModelEvents
import com.example.testgmf.shared.presentation.utils.LifeCycleViewObserver
import com.example.testgmf.shared.presentation.utils.ObserveAsEvents
import com.example.testgmf.shared.presentation.view.CommonGeneralError

@Composable
fun AllCountriesScreenRoot(
    allCountriesViewModel: AllCountriesViewModel = hiltViewModel(),
    onNavigateToDetails: (String) -> Unit,
) {
    val uiState by allCountriesViewModel.uiState.collectAsStateWithLifecycle()

    val isGeneralCommonErrorDialogVisible = remember { mutableStateOf(value = false) }

    ObserveAsEvents(flow = allCountriesViewModel.events) { events ->
        when (events) {
            is AllCountriesViewModelEvents.ShowErrorDialog -> {
                isGeneralCommonErrorDialogVisible.value = true
            }
            is AllCountriesViewModelEvents.NavigateToCountryDetails -> {
                onNavigateToDetails(events.countryName)
            }
        }
    }

    LifeCycleViewObserver(
        onCreate = {
            allCountriesViewModel.onAction(
                action = AllCountriesViewModelAction.OnAllCountriesScreenDisplayed,
            )
        },
    )

    AllCountriesScreen(
        state = uiState,
        onCountryClicked = { countryName ->
            allCountriesViewModel.onAction(
                action = AllCountriesViewModelAction.OnCountryClicked(countryName = countryName),
            )
        },
    )

    CommonGeneralError(
        shouldShowDialogState = isGeneralCommonErrorDialogVisible,
        onRetryButtonClicked = {
            allCountriesViewModel.onAction(
                action = AllCountriesViewModelAction.OnErrorDialogRetryButtonClicked,
            )
        }
    )
}

@Composable
private fun AllCountriesScreen(
    state: AllCountriesUIState,
    onCountryClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenContent(
        modifier = modifier,
        hasTopBar = false,
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = Spacing.sm),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(color = CardBackground)
                .padding(Spacing.md)
        ) {
            items(items = state.countries) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = Spacing.md),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(size = Rounding.xs))
                        .background(color = Grey)
                        .padding(all = Spacing.md)
                        .clickable(
                            onClick = {
                                onCountryClicked(it.countryName)
                            }
                        ),
                ) {
                    AsyncImage(
                        model = it.countryFlag,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(height = 200.dp, width = 300.dp),
                    )

                    Text(
                        text = it.countryName,
                        style = TextStyle(
                            color = Black,
                            fontSize = 32.sp,
                            fontWeight = FontWeight(weight = 800),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        if (state.isLoading) {
            Loader()
        }
    }
}

@PreviewLightDark
@Composable
private fun AllCountriesScreenPreview() {
    AllCountriesScreen(
        state = AllCountriesUIState(
            countries = listOf(
                CountryUIState(
                    countryName = "Country 1",
                    countryFlag = "https://flagcdn.com/w320/mc.png",
                ),
                CountryUIState(
                    countryName = "Country 2",
                    countryFlag = "https://flagcdn.com/w320/mc.png",
                ),
                CountryUIState(
                    countryName = "Country 3",
                    countryFlag = "https://flagcdn.com/w320/mc.png",
                ),
            ),
            isLoading = false,
        ),
        onCountryClicked = {},
    )
}