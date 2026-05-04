package com.example.tenretni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tenretni.ui.screens.main.MainScreen
import com.example.tenretni.ui.theme.TenretniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TenretniTheme {
                MainScreen()
            }
        }
    }
}
