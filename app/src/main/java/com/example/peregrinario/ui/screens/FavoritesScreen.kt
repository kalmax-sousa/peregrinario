package com.example.peregrinario.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import com.example.peregrinario.ui.components.TravelListItem

@ExperimentalMaterial3Api
@Composable
fun FavoritesScreen(
    onTravelSelected: (Travel) -> Unit,
    onFavoriteToggle: (Travel) -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarWithMenu(
                onSettingsClick = onSettingsClick,
                onHelpClick = onHelpClick,
                onFavoritesClick = onFavoritesClick,
                onHomeClick = onHomeClick
            )
        }
    ) { innerPadding ->
        val favoriteTravels = travelList.filter { it.isFavorite }

        if (favoriteTravels.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Você ainda não adicionou favoritos.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
            ) {
                items(favoriteTravels) { travel ->
                    TravelListItem(
                        travel = travel,
                        onTravelSelected = { onTravelSelected(it) },
                        onFavoriteToggle = { onFavoriteToggle(it) }
                    )
                }
            }
        }
    }
}
