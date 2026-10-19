package com.example.vkstart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vkstart.ui.screens.AppListScreen
import com.example.vkstart.ui.theme.VkstartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkstartTheme {
                AppListScreen()
            }
        }
    }
}
