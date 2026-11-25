package com.nyp.scaffoldingapp.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nyp.scaffoldingapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: (isLoggedIn: Boolean) -> Unit) {
    // Keeps the latest callback without restarting the effect below.
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        delay(1500) // minimum time to show the logo
        val isLoggedIn = false // TODO: replace with a real session check
        currentOnFinished(isLoggedIn)
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.ic_star), // swap for your logo
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text("Scaffolding App", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            CircularProgressIndicator()
        }
    }
}