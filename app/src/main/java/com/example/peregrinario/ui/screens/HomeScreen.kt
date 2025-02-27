package com.example.peregrinario.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peregrinario.models.Travel
import com.example.peregrinario.models.travelList
import com.example.peregrinario.ui.components.TravelListItem
import com.example.peregrinario.ui.components.TopAppBarWithMenu
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    onTravelSelected : (Travel) -> Unit,
    recentSearches : MutableList<Travel>
) {
    var searchQuery by remember { mutableStateOf("") }
    var travels by remember { mutableStateOf(travelList) }
    var filteredTravels by remember { mutableStateOf(travels) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        filteredTravels = travels
        isLoading = false
    }

    val coroutineScope = rememberCoroutineScope()

    fun searchTravels(query: String) {
        isLoading = true
        coroutineScope.launch {
            delay(2000)
            filteredTravels = travels.filter { it.destinationName.contains(query, ignoreCase = true) }
            isLoading = false
        }
    }

    Column{
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                searchTravels(it)
            },
            label = { Text("Pesquisar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recentSearches) { travel ->
                Button(onClick = { onTravelSelected(travel) }) {
                    Text(
                        text = travel.destinationName,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                items(filteredTravels) { travel ->
                    TravelListItem(
                        travel = travel,
                        onTravelSelected = { selectedTravel ->
                            if (!recentSearches.contains(selectedTravel)) {
                                if (recentSearches.size >= 5) {
                                    recentSearches.removeAt(recentSearches.size - 1)
                                }
                                recentSearches.add(0, selectedTravel)
                            }
                            onTravelSelected(selectedTravel)
                        },
                        onFavoriteToggle = { favoriteTravel ->
                            favoriteTravel.isFavorite.value = !favoriteTravel.isFavorite.value
                        }
                    )
                }
            }
        }
    }

}

