package com.example.peregrinario.ui.screens

import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peregrinario.models.Travel
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import com.example.peregrinario.utils.scheduleNotification
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun DetailsScreen(
    travel: Travel
) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var audioCurrent by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = travel.destinationName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Button(
                onClick = {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val localDate = LocalDate.parse(travel.startDate, formatter)

                    Toast.makeText(context, "Lembrete registrado!", Toast.LENGTH_SHORT).show()

                    scheduleNotification(
                        context,
                        localDate.year,
                        localDate.monthValue,
                        localDate.dayOfMonth,
                        5,
                        0
                    )
                },
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Lembrar",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.surface
                    )
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Lembrar()",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Detalhes da Viagem",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = travel.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Inicio: ${travel.startDate} - Fim: ${travel.endDate}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ){
            items(travel.audioRes){ audio ->
                Button(onClick = {
                    if (isPlaying) {
                        mediaPlayer?.pause()
                        isPlaying = false
                        Log.d("AudioPlayer", "Audio paused")
                    } else {
                        if (mediaPlayer == null || audio != audioCurrent) {
                            audioCurrent = audio
                            mediaPlayer = MediaPlayer.create(context, audio)
                            mediaPlayer?.setOnCompletionListener {
                                it.release()
                                mediaPlayer = null
                                isPlaying = false
                            }
                        }
                        mediaPlayer?.start()
                        isPlaying = true
                    }
                }) {
                    Text(
                        (if (isPlaying && audioCurrent == audio) "Pausar " else "Executar ") +
                        context.resources.getResourceEntryName(audio),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(0.dp)
        ) {
            items(travel.imageRes) { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Imagem de ${travel.destinationName}",
                    modifier = Modifier
                        .size(350.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp),
                )
            }
        }
    }
}


