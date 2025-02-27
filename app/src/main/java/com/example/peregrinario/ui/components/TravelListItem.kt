package com.example.peregrinario.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.peregrinario.models.Travel
import kotlinx.coroutines.launch

@Composable
fun TravelListItem(travel: Travel, onTravelSelected: (Travel) -> Unit, onFavoriteToggle: (Travel) -> Unit) {
    var isFavoriting by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(travel.isFavorite.value) }
    val coroutineScope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1f,
        animationSpec = if (isFavorite) keyframes {
            durationMillis = 1000
            1f at 0 with FastOutSlowInEasing
            3f at 500 with FastOutSlowInEasing
            1.2f at 1000
        } else keyframes {
            durationMillis = 1000
            1f at 0 with FastOutSlowInEasing
            1.5f at 500 with FastOutSlowInEasing
            1f at 1000
        }
    )


    val alpha by animateFloatAsState(
        targetValue = if (isFavorite) 1f else 0.99f,
        animationSpec = keyframes {
            durationMillis = 1000
            1f at 0 with FastOutSlowInEasing
            0.5f at 500 with FastOutSlowInEasing
            1f at 1000
        }
    )

    val iconColor by animateColorAsState(
        targetValue = if (isFavorite) Color.Green else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(travel.imageRes[0])
                            .crossfade(true)
                            .build(),
                        contentDescription = "${travel.destinationName} image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = travel.destinationName,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                if (isFavoriting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(
                        onClick = {
                            isFavoriting = true
                            coroutineScope.launch {
                                onFavoriteToggle(travel)
                                isFavorite = !isFavorite
                                isFavoriting = false
                            }
                        },
                        enabled = !isFavoriting,
                        modifier = Modifier
                            .scale(scale)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = iconColor,
                            modifier = Modifier
                                .alpha(alpha)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Início: ${travel.startDate.replace("-", "/")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Fim: ${travel.endDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = travel.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onTravelSelected(travel) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = "Detalhes",
                color = MaterialTheme.colorScheme.surface,
            )
        }
    }
}
