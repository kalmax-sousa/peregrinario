package com.example.peregrinario.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TravelListItem
import com.example.peregrinario.ui.components.TopAppBarWithMenu

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    onTravelSelected : (Travel) -> Unit,
    onSettingsClick: () -> Unit,
    onHelpClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var travels by remember { mutableStateOf(travelList) }
    var filteredTravels = remember(searchQuery, travels) {
        travels.filter { it.destinationName.contains(searchQuery, ignoreCase = true) }
    }

    val recentSearches = remember { mutableStateListOf<Travel>() }

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
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recentSearches) { travel ->
                    Button(onClick = { onTravelSelected(travel) }) {
                        Text(travel.destinationName)
                    }
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                items(filteredTravels) { travel ->
                    TravelListItem (
                        travel = travel,
                        onTravelSelected = { selectedTravel ->
                            if (!recentSearches.contains(selectedTravel)) {
                                recentSearches.add(0, selectedTravel)
                            }
                            onTravelSelected(selectedTravel)
                        },
                        onFavoriteToggle = { favoriteTravel ->
                            travels = travels.map {
                                if (it.destinationName == favoriteTravel.destinationName){
                                    it.copy(isFavorite = !favoriteTravel.isFavorite)
                                }
                                else
                                    it
                            }
                            favoriteTravel.isFavorite = !favoriteTravel.isFavorite
                        }
                    )
                }
            }
        }
    }
}

