package com.reutiliza.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.reutiliza.app.ui.navigation.ReUtilizaNavGraph
import com.reutiliza.app.ui.theme.ReUtilizaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReUtilizaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ReUtilizaNavGraph(isLoggedIn = auth.currentUser != null)
                }
            }
        }
    }
}
