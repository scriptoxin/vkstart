package com.example.vkstart.presentation.appdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vkstart.R

@Composable
fun InstallButton(
    installState: InstallState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonColors = when (installState) {
        is InstallState.Installing -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
        is InstallState.Installed -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
        is InstallState.NotInstalled -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    }

    val text = when (installState) {
        is InstallState.NotInstalled -> stringResource(R.string.install)
        is InstallState.Installing -> "Отменить (${installState.progress}%)"
        is InstallState.Installed -> "Установлено"
    }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = buttonColors,
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier,
    ) {
        Text(text = text)
    }
}
