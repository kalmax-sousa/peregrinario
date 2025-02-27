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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peregrinario.models.ConnectivityObserver
import com.example.peregrinario.models.NetworkConnectivityObserver
import com.example.peregrinario.navigation.NavGraph
import com.example.peregrinario.repository.AuthRepository
import com.example.peregrinario.repository.PreferencesRepository
import com.example.peregrinario.ui.theme.PeregrinarioAppTheme
import com.example.peregrinario.viewmodel.AuthViewModel
import com.example.peregrinario.viewmodel.AuthViewModelFactory
import com.example.peregrinario.viewmodel.ConnectivityViewModel
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

        val preferencesRepository = PreferencesRepository(this)
        val authRepository = AuthRepository(this)
        val connectivityObserver = NetworkConnectivityObserver(this)

        setContent {
            val preferencesViewModel: PreferencesViewModel = viewModel(factory = PreferencesViewModelFactory(preferencesRepository))
            val authViewModel: AuthViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AuthViewModel(authRepository) as T
                }
            })
            val connectivityViewModel: ConnectivityViewModel = viewModel(factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ConnectivityViewModel(connectivityObserver) as T
                }
            })

            val networkStatus by connectivityViewModel.networkStatus.collectAsState()

            PeregrinarioAppTheme(darkTheme = preferencesViewModel.darkMode.collectAsState().value) {
                Scaffold(
                    topBar = {
                        if (networkStatus == ConnectivityObserver.Status.Lost ||
                            networkStatus == ConnectivityObserver.Status.Unavailable) {
                            TopAppBar(
                                title = { Text("Sem conexão com a internet", color = Color.White) },
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red)
                                // colors = TopAppBarDefaults.topAppBarColors(backgroundColor = Color.Red)
                            )
                        }
                    }
                ) { innerPadding ->
                    NavGraph(preferencesViewModel, authViewModel, Modifier.padding(innerPadding))
                }
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
