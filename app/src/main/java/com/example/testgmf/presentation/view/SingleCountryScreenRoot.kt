package com.example.testgmf.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testgmf.shared.presentation.view.ScreenContent

@Composable
fun SingleCountryScreenRoot(
    countryName: String,
    onBackClicked: () -> (Unit),
) {
    ScreenContent(
        topBarTitle = countryName,
        onBackClicked = onBackClicked,
    ) {
        Text(countryName,
            Modifier.padding(paddingValues = it))
    }
}