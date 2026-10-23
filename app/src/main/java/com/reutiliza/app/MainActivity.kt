package com.reutiliza.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.reutiliza.app.data.local.SessionManager
import com.reutiliza.app.ui.navigation.ReUtilizaNavGraph
import com.reutiliza.app.ui.theme.ReUtilizaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var isLoggedIn by mutableStateOf(false)
        var ready      by mutableStateOf(false)

        lifecycleScope.launch {
            isLoggedIn = sessionManager.getToken() != null
            ready      = true
        }

        setContent {
            ReUtilizaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (ready) {
                        ReUtilizaNavGraph(isLoggedIn = isLoggedIn)
                    }
                }
            }
        }
    }
}
