package com.example.peregrinario

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.peregrinario.navigation.NavGraph
import com.example.peregrinario.ui.theme.PeregrinarioAppTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeregrinarioAppTheme (darkTheme = false) {

                NavGraph()
            }
        }
    }
}
