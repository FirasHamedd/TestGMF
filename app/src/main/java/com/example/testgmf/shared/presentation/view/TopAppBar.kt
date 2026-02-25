package com.example.testgmf.shared.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.testgmf.ui.theme.Black
import com.example.testgmf.ui.theme.Grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleAndBackTopAppBar(
    title: String,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(text = title) },
        colors = TopAppBarColors(
            containerColor = Grey,
            titleContentColor = Black,
            navigationIconContentColor = Black,
            actionIconContentColor = Black,
            scrolledContainerColor = Black,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClicked,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
@PreviewLightDark
private fun SearchAndBackTopAppBarPreview() {
    Column {
        TitleAndBackTopAppBar(
            title = "Title",
            onBackClicked = {},
        )
    }
}
