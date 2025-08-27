package com.victory.mediroom.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.navigation.ROUT_APPOINTMENT
import com.victory.mediroom.navigation.ROUT_GALLERY
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_PROFILE
import com.victory.mediroom.navigation.ROUT_REVIEW
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(0) }

    // State for the review input field
    var reviewText by remember { mutableStateOf("") }

    Scaffold(
        // Top App Bar
        topBar = {
            TopAppBar(
                title = { Text("MEDIROOM") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUT_HOME)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightpurple2,
                    titleContentColor = purple,
                    navigationIconContentColor = purple
                )
            )
        },

        // Bottom Navigation Bar
        bottomBar = {
            NavigationBar(containerColor = lightpurple2) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        navController.navigate(ROUT_HOME)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Create, contentDescription = "Reviews") },
                    label = { Text("Reviews") },
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        navController.navigate(ROUT_REVIEW)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Appointment") },
                    label = { Text("Appointment") },
                    selected = selectedIndex == 2,
                    onClick = {
                        selectedIndex = 2
                        navController.navigate(ROUT_APPOINTMENT)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 3,
                    onClick = {
                        selectedIndex = 3
                        navController.navigate(ROUT_PROFILE)
                    }
                )
            }
        },

        // Floating Action Button
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ROUT_GALLERY)
                },
                containerColor = purple
            ) {
                Icon(Icons.Default.Face, contentDescription = "gallery")
            }
        },

        // Main Content Area
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(lightpurple)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp) // Padding for content
            ) {

                // Heading for the Review Section
                Text(
                    text = "Write a Review",
                    color = purple,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // TextField for entering a review
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Your review") },
                    placeholder = { Text("Enter your experience...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Submit Button (non-functional for now)
                Button(
                    onClick = {
                        // You can later implement actual submission logic here
                        reviewText = "" // Clear text after "submission"
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Submit")
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview(){
    ReviewScreen(navController= rememberNavController())
}