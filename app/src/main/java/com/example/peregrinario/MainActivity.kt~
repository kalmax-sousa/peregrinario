package com.example.planetapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.planetapp.navigation.NavGraph

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavGraph(
                onSettingsClick = {
                    Toast.makeText(this, "Configurações clicada", Toast.LENGTH_SHORT).show()
                },
                onHelpClick = {
                    Toast.makeText(this, "Ajuda clicada", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
