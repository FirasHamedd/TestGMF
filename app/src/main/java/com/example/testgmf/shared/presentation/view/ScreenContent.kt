package com.example.testgmf.shared.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testgmf.presentation.ui.theme.Grey


@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    topBarTitle: String = String(),
    hasTopBar: Boolean = true,
    onBackClicked: () -> (Unit) = {},
    content: @Composable (padding: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (hasTopBar) {
                Column {
                    TitleAndBackTopAppBar(
                        title = topBarTitle,
                        onBackClicked = onBackClicked,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        color = Grey,
                    )
                }
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}