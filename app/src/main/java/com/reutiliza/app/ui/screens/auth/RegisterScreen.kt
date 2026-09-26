package com.reutiliza.app.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reutiliza.app.ui.theme.Green50
import com.reutiliza.app.ui.theme.Green500
import com.reutiliza.app.viewmodel.AuthUiState
import com.reutiliza.app.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    vm: AuthViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsState()
    var name            by remember { mutableStateOf("") }
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPass        by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) { vm.resetState(); onRegisterSuccess() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green50)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        Text("🌿", fontSize = 48.sp)
        Spacer(Modifier.height(4.dp))
        Text(
            "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Green500, fontWeight = FontWeight.ExtraBold
            )
        )
        Text("Únete a la comunidad ReUtiliza", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Spacer(Modifier.height(32.dp))

        Card(
            modifier  = Modifier.fillMaxWidth(),
            shape     = RoundedCornerShape(24.dp),
            colors    = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                listOf(
                    Triple(name,            { v: String -> name = v },            "Nombre completo")     to Icons.Filled.Person,
                    Triple(email,           { v: String -> email = v },           "Correo electrónico")  to Icons.Filled.Email,
                ).forEach { (triple, icon) ->
                    val (value, onChange, label) = triple
                    OutlinedTextField(
                        value         = value,
                        onValueChange = onChange,
                        label         = { Text(label) },
                        leadingIcon   = { Icon(icon, null) },
                        modifier      = Modifier.fillMaxWidth(),
                        singleLine    = true,
                        shape         = RoundedCornerShape(12.dp),
                        keyboardOptions = if (label.contains("Correo"))
                            KeyboardOptions(keyboardType = KeyboardType.Email)
                        else KeyboardOptions.Default
                    )
                    Spacer(Modifier.height(12.dp))
                }

                // Contraseña
                OutlinedTextField(
                    value         = password,
                    onValueChange = { password = it },
                    label         = { Text("Contraseña") },
                    leadingIcon   = { Icon(Icons.Filled.Lock, null) },
                    trailingIcon  = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(if (showPass) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, null)
                        }
                    },
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier    = Modifier.fillMaxWidth(),
                    singleLine  = true,
                    shape       = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value         = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label         = { Text("Confirmar contraseña") },
                    leadingIcon   = { Icon(Icons.Filled.Lock, null) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier    = Modifier.fillMaxWidth(),
                    singleLine  = true,
                    shape       = RoundedCornerShape(12.dp)
                )

                if (uiState is AuthUiState.Error) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        (uiState as AuthUiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick  = { vm.register(name, email, password, confirmPassword) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = Green500),
                    enabled  = uiState !is AuthUiState.Loading
                ) {
                    if (uiState is AuthUiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Registrarme", style = MaterialTheme.typography.labelLarge.copy(color = Color.White))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión", style = MaterialTheme.typography.bodyMedium.copy(color = Green500))
        }
    }
}
