package com.example.testgmf.shared.presentation.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.testgmf.R

@Composable
fun CommonGeneralError(
    errorMessage: String = stringResource(id = R.string.common_general_dialog_message),
    dismissButtonLabel: String = stringResource(id = R.string.common_general_dialog_dismiss_button),
    retryButtonLabel: String = stringResource(id = R.string.common_general_dialog_retry_button),
    shouldShowDialogState: MutableState<Boolean> = remember { mutableStateOf(value = false) },
    onDismissButtonClicked: () -> Unit = {},
    onRetryButtonClicked: () -> Unit = {},
) {
    if (shouldShowDialogState.value) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = {
                        onRetryButtonClicked()
                        shouldShowDialogState.value = false
                    }
                )
                {
                    Text(text = retryButtonLabel)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissButtonClicked()
                        shouldShowDialogState.value = false
                    }
                )
                {
                    Text(text = dismissButtonLabel)
                }
            },
            text = { Text(text = errorMessage) }
        )
    }
}

@Preview
@Composable
fun CommonGeneralErrorPReview() {
    CommonGeneralError()
}