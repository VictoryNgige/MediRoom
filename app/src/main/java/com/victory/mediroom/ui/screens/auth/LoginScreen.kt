package com.victory.mediroom.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_GALLERY
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_REGISTER
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import com.victory.mediroom.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Observe login logic
    LaunchedEffect(authViewModel) {
        authViewModel.loggedInUser = { user ->
            if (user == null) {
                Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            } else {
                if (user.role=="Admin"){
                    navController.navigate(ROUT_GALLERY)
                }
                else if (user.role == "Doctor"){
                    navController.navigate(ROUT_HOME)
                }
                else{
                    navController.navigate(ROUT_HOME)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                purple1
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

                Image(
                painter = painterResource(R.drawable.hospital_logo),
                contentDescription = "Hospital Logo",
                modifier = Modifier.size(120.dp)
                )

                Text(
                    text = "Welcome Back!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = purple,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Manage your appointments and records securely.",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = purple,
                    textAlign = TextAlign.Center
                )

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), shape = RoundedCornerShape(15.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = purple,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                shape = RoundedCornerShape(15.dp)
            )

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(image, contentDescription = if (passwordVisible) "Hide Password" else "Show Password")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), shape = RoundedCornerShape(15.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = purple,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                shape = RoundedCornerShape(15.dp)
            )

            // Login Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.loginUser(email, password)
                    }
                },
                modifier = Modifier
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purple),
                shape = RoundedCornerShape(15.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            // Navigate to Register
            TextButton(onClick = { navController.navigate(ROUT_REGISTER) }) {
                Text(
                    "Don't have an account? Register",
                    color = Color.White.copy(alpha = 0.85f),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

