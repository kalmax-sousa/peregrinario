package com.example.peregrinario

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peregrinario.navigation.NavGraph
import com.example.peregrinario.repository.PreferencesRepository
import com.example.peregrinario.ui.theme.PeregrinarioAppTheme
import com.example.peregrinario.viewmodel.PreferencesViewModel
import com.example.peregrinario.viewmodel.PreferencesViewModelFactory

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("App", "Permissão concedida para enviar notificações.")
            } else {
                Log.d("App", "Permissão negada para enviar notificações.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }

        val preferencesRepository = PreferencesRepository(this)

        setContent {
            val preferencesViewModel : PreferencesViewModel = viewModel(factory = PreferencesViewModelFactory(preferencesRepository))

            PeregrinarioAppTheme(darkTheme = preferencesViewModel.darkMode.collectAsState().value) {
                NavGraph(preferencesViewModel)
            }
        }
    }

    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
