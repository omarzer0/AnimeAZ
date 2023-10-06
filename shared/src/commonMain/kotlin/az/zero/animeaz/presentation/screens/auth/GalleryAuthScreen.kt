package az.zero.animeaz.presentation.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.biometry.compose.BindBiometryAuthenticatorEffect
import dev.icerock.moko.biometry.compose.BiometryAuthenticatorFactory
import dev.icerock.moko.biometry.compose.rememberBiometryAuthenticatorFactory
import io.github.xxfast.decompose.router.rememberOnRoute

@Composable
fun GalleryAuthScreen(
    onAuthSuccess: () -> Unit
) {
    val biometryFactory: BiometryAuthenticatorFactory = rememberBiometryAuthenticatorFactory()
    val viewModel = rememberOnRoute(instanceClass = GalleryAuthViewModel::class) {
        GalleryAuthViewModel(it, biometryFactory.createBiometryAuthenticator())
    }
    val authState by viewModel.authState.collectAsState()

    BindBiometryAuthenticatorEffect(viewModel.biometryAuthenticator)

    if (authState.isSuccess) {
        onAuthSuccess()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val text = when {
                authState.isSuccess -> "Hooray! Success"
                authState.throwable != null -> "Failed. Please try again"
                else -> "Use Biometric auth to continue"
            }

            val color = when {
                authState.isSuccess -> Color.Green
                authState.throwable != null -> Color.Red
                else -> MaterialTheme.colorScheme.onPrimary
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = color
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.5f),
                onClick = {
                    viewModel.onAuthClick()
                }
            ) {
                Text(text = "Authenticate")
            }
        }

    }
}