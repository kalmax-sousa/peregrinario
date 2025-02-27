package com.example.peregrinario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peregrinario.viewmodel.PreferencesViewModel

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(preferencesViewModel: PreferencesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Configurações", style = MaterialTheme.typography.titleLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tema escuro", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = preferencesViewModel.darkMode.collectAsState().value,
                onCheckedChange = { preferencesViewModel.setDarkMode(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        NotificationSettings(preferencesViewModel)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Animações de tela",
                style = MaterialTheme.typography.bodyLarge
                )
            Switch(
                checked = preferencesViewModel.displayAnimations.collectAsState().value,
                onCheckedChange = { preferencesViewModel.setDisplayAnimations(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.surface
                )
            )
        }

        Button(
            onClick = {
                preferencesViewModel.setDarkMode(false)
                preferencesViewModel.setNotifications(true)
                preferencesViewModel.setDisplayAnimations(true)
            },

        ) {
            Text(
                text = "Restaurar configurações",
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
fun NotificationSettings(preferencesViewModel: PreferencesViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Notificações", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ativar notificações", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = preferencesViewModel.notifications.collectAsState().value,
                onCheckedChange = { preferencesViewModel.setNotifications(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
