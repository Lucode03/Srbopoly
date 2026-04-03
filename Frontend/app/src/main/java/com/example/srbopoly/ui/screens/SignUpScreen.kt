package com.example.srbopoly.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.srbopoly.ui.CustomTextField
import com.example.srbopoly.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(modifier: Modifier = Modifier,
                 navController: NavController,
                 authViewModel: AuthViewModel
)
{
    val username by authViewModel.usernameSignup.collectAsStateWithLifecycle()
    val password by authViewModel.passwordSignup.collectAsStateWithLifecycle()
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()
    val user by authViewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(user) {
        user?.let {
            navController.navigate("main")
        }
    }

    LaunchedEffect(error) {
        error?.let {

        }
    }

    val focusManager = LocalFocusManager.current
    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registracija",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(text = "Dobrodošli u aplikaciju!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height((30.dp)))
            HorizontalDivider(thickness = 1.dp, color = Color.Gray, modifier = Modifier.fillMaxWidth(0.9f))
            Spacer(modifier = Modifier.height((15.dp)))

            CustomTextField(
                value = username,
                onValueChange = { authViewModel.onUsernameSignupChange(it) },
                label = "Korisničko ime",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height((20.dp)))

            CustomTextField(
                value = password,
                onValueChange = { authViewModel.onPasswordSignupChange(it) },
                label = "Lozinka",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height((15.dp)))
            HorizontalDivider(thickness = 1.dp, color = Color.Gray, modifier = Modifier.fillMaxWidth(0.9f))
            Spacer(modifier = Modifier.height((15.dp)))
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Button(onClick = {
                authViewModel.signup()
            },
                enabled = !isLoading
            ) {
                Text(text="Registracija")
            }
            Spacer(modifier = Modifier.height((10.dp)))
            Text(text="Imate nalog? Prijavite se",
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }
    }
}