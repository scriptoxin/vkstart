package com.example.vkstart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.vkstart.ui.theme.VkstartTheme


class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkstartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstScreen(
                        modifier = Modifier.padding(innerPadding),
                        onOpenSecondActivity = { text ->
                            val intent = Intent(this, SecondActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, text)
                            }
                            startActivity(intent)
                        },
                        onCallFriend = { phoneNumber ->
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel:$phoneNumber".toUri()
                            }
                            startActivity(intent)
                        },
                        onShareText = { text ->
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }
                            val chooser = Intent.createChooser(intent, "Share via...")
                            startActivity(chooser)
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "com.example.vkstart.EXTRA_MESSAGE"
    }
}

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    onOpenSecondActivity: (String) -> Unit,
    onCallFriend: (String) -> Unit,
    onShareText: (String) -> Unit
) {
    var explicitText by remember { mutableStateOf("") }
    var explicitError by remember { mutableStateOf<String?>(null) }

    var phoneNumber by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }

    var shareText by remember { mutableStateOf("") }
    var shareError by remember { mutableStateOf<String?>(null) }

    val localContext = LocalContext.current
    val phoneRegex = Regex("^[+]?[0-9\\s\\-()]{3,20}$")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Intent Demo App",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // 1. Explicit Intent Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "1. Explicit Intent",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = explicitText,
                    onValueChange = {
                        explicitText = it
                        if (explicitError != null) explicitError = null
                    },
                    label = { Text("Text for Second Activity") },
                    isError = explicitError != null,
                    supportingText = {
                        if (explicitError != null) {
                            Text(text = explicitError!!, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (explicitText.isBlank()) {
                            explicitError = "Please enter some text"
                            Toast.makeText(localContext, "Text cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            explicitError = null
                            onOpenSecondActivity(explicitText.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Open Second Activity")
                }
            }
        }

        // 2. Implicit Intent Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "2. Implicit Intent (ACTION_DIAL)",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        if (phoneError != null) phoneError = null
                    },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isError = phoneError != null,
                    supportingText = {
                        if (phoneError != null) {
                            Text(text = phoneError!!, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        val trimmed = phoneNumber.trim()
                        if (trimmed.isBlank()) {
                            phoneError = "Phone number cannot be empty"
                            Toast.makeText(
                                localContext,
                                "Please enter a phone number",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!trimmed.matches(phoneRegex)) {
                            phoneError = "Invalid phone number format"
                            Toast.makeText(
                                localContext,
                                "Please enter a valid phone number",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            phoneError = null
                            onCallFriend(trimmed)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Call a Friend")
                }
            }
        }

        // 3. System Intent Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "3. System Intent (ACTION_SEND)",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = shareText,
                    onValueChange = {
                        shareText = it
                        if (shareError != null) shareError = null
                    },
                    label = { Text("Text to Share") },
                    isError = shareError != null,
                    supportingText = {
                        if (shareError != null) {
                            Text(text = shareError!!, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        if (shareText.isBlank()) {
                            shareError = "Share text cannot be empty"
                            Toast.makeText(
                                localContext,
                                "Please enter text to share",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            shareError = null
                            onShareText(shareText.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Share Text")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FirstScreenPreview() {
    VkstartTheme {
        FirstScreen(
            onOpenSecondActivity = { text ->
                // Dummy action for preview
            },
            onCallFriend = { phoneNumber ->
                // Dummy action for preview
            },
            onShareText = { text ->
                // Dummy action for preview
            }
        )
    }
}
