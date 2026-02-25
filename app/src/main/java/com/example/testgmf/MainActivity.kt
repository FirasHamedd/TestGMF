package com.example.testgmf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.testgmf.presentation.ui.theme.TestGMFTheme
import com.example.testgmf.presentation.view.MainScreenRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestGMFTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainScreenRoot()
                }
            }
        }
    }
}