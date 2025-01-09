package com.example.peregrinario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(isDarkTheme: MutableState<Boolean>, isNotificationsEnabled: MutableState<Boolean>) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                    checked = isDarkTheme.value,
                    onCheckedChange = { isDarkTheme.value = it }
                )
            }

            NotificationSettings(isNotificationsEnabled)

            Button(onClick = {
                isDarkTheme.value = false
                isNotificationsEnabled.value = true
            }) {
                Text(text = "Restaurar configurações")
            }
        }
    }
}

@Composable
fun NotificationSettings(isNotificationsEnabled: MutableState<Boolean>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Notificações", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Ativar notificações", style = MaterialTheme.typography.bodyLarge)
            Switch(
                checked = isNotificationsEnabled.value,
                onCheckedChange = { isNotificationsEnabled.value = it }
            )
        }
    }
}
