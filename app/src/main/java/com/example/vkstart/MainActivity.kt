package com.example.vkstart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vkstart.ui.navigation.AppNavigation
import com.example.vkstart.ui.theme.VkstartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkstartTheme {
                AppNavigation()
            }
        }
    }
}
