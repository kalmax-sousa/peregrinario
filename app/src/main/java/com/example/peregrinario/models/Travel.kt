package com.example.peregrinario.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.peregrinario.R

data class Travel(
    val id: Int,
    val destinationName: String,
    val description : String,
    val startDate: String,
    val endDate: String,
    val imageRes: List<Int>,
    val audioRes: List<Int>,
    var isFavorite: MutableState<Boolean> = mutableStateOf(false)
)

val travelList = listOf(
    Travel(
        id = 1,
        destinationName = "Rio de Janeiro",
        description = "Desfrute das belas praias de Copacabana e Ipanema, e visite o icônico Cristo Redentor.",
        startDate = "01/05/2025",
        endDate = "10/05/2025",
        imageRes = listOf(R.drawable.rio1, R.drawable.rio2, R.drawable.rio3),
        audioRes = listOf(R.raw.rio_music),
        isFavorite = mutableStateOf(true)
    ),
    Travel(
        id = 2,
        destinationName = "Salvador",
        description = "Explore a rica cultura baiana, com seu centro histórico no Pelourinho e praias maravilhosas.",
        startDate = "15/06/2025",
        endDate = "25/06/2025",
        imageRes = listOf(R.drawable.salvador1),
        audioRes = listOf(R.raw.salvador_intro, R.raw.salvador_music),
        isFavorite = mutableStateOf(false)
    ),
    Travel(
        id = 3,
        destinationName = "Foz do Iguaçu",
        description = "Admire as impressionantes Cataratas do Iguaçu, uma das maravilhas naturais do mundo.",
        startDate = "10/07/2025",
        endDate = "20/07/2025",
        imageRes = listOf(R.drawable.foz1, R.drawable.foz2, R.drawable.foz3, R.drawable.foz4),
        audioRes = listOf(R.raw.foz_music, R.raw.foz_waterfalls),
        isFavorite = mutableStateOf(true)
    ),
    Travel(
        id = 4,
        destinationName = "Florianópolis",
        description = "Aproveite as praias paradisíacas da Ilha da Magia e explore a rica culinária local.",
        startDate = "01/09/2025",
        endDate = "10/09/2025",
        imageRes = listOf(R.drawable.floripa1, R.drawable.floripa2),
        audioRes = listOf(R.raw.floripa_music),
        isFavorite = mutableStateOf(false)
    ),
    Travel(
        id = 5,
        destinationName = "Manaus",
        description = "Descubra a Amazônia, com sua biodiversidade incrível e passeios pelo Rio Negro.",
        startDate = "20/11/2025",
        endDate = "30/11/2025",
        imageRes = listOf(R.drawable.manaus1),
        audioRes = listOf(R.raw.manaus_music, R.raw.manaus_forest),
        isFavorite = mutableStateOf(false)
    ),
    Travel(
        id = 6,
        destinationName = "Gramado",
        description = "Visite esta charmosa cidade no sul do Brasil, famosa por seu clima europeu e chocolate artesanal.",
        startDate = "10/04/2025",
        endDate = "20/04/2025",
        imageRes = listOf(R.drawable.gramado1, R.drawable.gramado2, R.drawable.gramado3),
        audioRes = listOf(R.raw.gramado_music),
        isFavorite = mutableStateOf(false)
    )
)
