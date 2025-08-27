package com.victory.mediroom.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.victory.mediroom.model.User
import com.victory.mediroom.navigation.ROUT_LOGIN
import com.victory.mediroom.viewmodel.AuthViewModel
import com.victory.mediroom.R
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Patient") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val roleOptions = listOf("Patient", "Doctor", "Admin")
    var roleDropdownExpanded by remember { mutableStateOf(false) }

    val animatedAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
        label = "Fade-in animation"
    )

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
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
                Text(
                    "Create Your Account",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = purple,
                    modifier = Modifier.alpha(animatedAlpha),
                    textAlign = TextAlign.Center
                )
            }

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(15.dp)),
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

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(15.dp)),
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

            // Role Dropdown
            ExposedDropdownMenuBox(
                expanded = roleDropdownExpanded,
                onExpandedChange = { roleDropdownExpanded = !roleDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = role,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Role") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = roleDropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(15.dp)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = purple,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    shape = RoundedCornerShape(15.dp)
                )

                ExposedDropdownMenu(
                    expanded = roleDropdownExpanded,
                    onDismissRequest = { roleDropdownExpanded = false }
                ) {
                    roleOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                role = option
                                roleDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(if (passwordVisible) R.drawable.passwordshow else R.drawable.passwordhide),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(15.dp)),
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

            // Confirm Password Field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(if (confirmPasswordVisible) R.drawable.passwordshow else R.drawable.passwordhide),
                            contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(15.dp)),
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

            // Register Button
            Button(
                onClick = {
                    when {
                        username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                        }

                        password != confirmPassword -> {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            authViewModel.registerUser(
                                User(username = username, email = email, role = role, password = password)
                            )
                            onRegisterSuccess()
                        }
                    }
                },
                modifier = Modifier
                    .height(55.dp)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purple),
                shape = RoundedCornerShape(15.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp)
            ) {
                Text("Register", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            // Login Prompt
            TextButton(
                onClick = { navController.navigate(ROUT_LOGIN) }
            ) {
                Text(
                    "Already have an account? Login",
                    color = Color.White.copy(alpha = 0.85f),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
