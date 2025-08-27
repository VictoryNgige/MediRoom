package com.victory.mediroom.ui.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_LOGIN
import com.victory.mediroom.navigation.ROUT_REGISTER
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Start1Screen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFCFF), Color(0xFFDAB1DA))
                ),
                shape = RoundedCornerShape(12.dp)
            ),
             // Soft brand color for calm
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // App Logo or illustration
            Image(
                painter = painterResource(R.drawable.hospital_logo),
                contentDescription = "Hospital Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to MediRoom",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = purple
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your Health, Our Care",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(40.dp))

            // Login Button
            Button(
                onClick = { navController.navigate(ROUT_LOGIN) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purple)
            ) {
                Text("Login", fontSize = 18.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Register Button (outline style)
            OutlinedButton(
                onClick = { navController.navigate(ROUT_REGISTER) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = purple)
            ) {
                Text("Register", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Security reassurance text
            Text(
                text = "Secure and private — your data is protected.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Start1ScreenPreview(){
    Start1Screen(rememberNavController())
}