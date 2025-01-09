package com.example.peregrinario.ui.screens

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@Composable
fun HelpScreen(isNotificationsEnabled: Boolean) {
    val context = LocalContext.current
    var userMessage by remember { mutableStateOf("") }

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Perguntas Frequentes",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            FAQItem(
                question = "Como cadastro uma nova viagem?",
                answer = "Você pode cadastrar uma nova viagem clicando no botão 'Adicionar Viagem' na tela inicial."
            )

            FAQItem(
                question = "Posso exportar minhas viagens?",
                answer = "Sim, vá até os detalhes da viagem e selecione a opção de exportar para PDF."
            )

            FAQItem(
                question = "Como altero meu tema?",
                answer = "Acesse o menu de configurações no seu perfil e selecione o tema desejado."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Fale Conosco",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            BasicTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
                    .padding(8.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

            Button(
                onClick = {
                    sendHelpNotification(context, isNotificationsEnabled)
                    Toast.makeText(context, "Mensagem enviada com sucesso!", Toast.LENGTH_LONG).show()
                    userMessage = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Enviar")
            }
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = answer,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            color = Color.Gray,
            textAlign = TextAlign.Justify
        )
    }
}

fun sendHelpNotification(context: Context, isNotificationsEnabled: Boolean) {
    if (!isNotificationsEnabled) {
        Log.d("App", "Notificações desativadas. Não enviando a notificação.")
        return
    }

    val channelId = "help_channel"
    val channelName = "Ajuda"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Verifica se a versão do Android é 8.0 (API 26) ou superior
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Cria o canal de notificação
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Canal para notificações de ajuda"
        }
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Ajuda Solicitada")
        .setContentText("Sua solicitação de ajuda foi enviada com sucesso.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(1, notification)
    }
}
