@file:OptIn(ExperimentalMaterial3Api::class)

package com.victory.mediroom.ui.screens.gallery

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.victory.mediroom.navigation.*
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


@Composable
fun ViewGalleryScreen(navController: NavController) {
    val context = LocalContext.current
    val isInPreview = LocalInspectionMode.current

    // Safe ViewModel initialization
    val viewModel: GalleryViewModel? = (if (!isInPreview) {
        val appContext = context.applicationContext
        if (appContext is Application) {

            class GalleryViewModelFactory(
                private val application: Application
            ) : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return when {
                        modelClass.isAssignableFrom(GalleryViewModel::class.java) ->
                            GalleryViewModel(application) as T
                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }

        } else null
    } else null) as GalleryViewModel?

    // Collect state only when ViewModel is available
    val savedItems by viewModel?.galleryItems?.collectAsState() ?: remember { mutableStateOf(emptyList()) }

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MEDIROOM") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_HOME) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
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
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Appointments") },
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

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUT_GALLERY) },
                containerColor = purple
            ) {
                Icon(Icons.Default.Face, contentDescription = "Gallery")
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(lightpurple)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (savedItems.isEmpty()) {
                    Text("No items in gallery.", color = Color.DarkGray)
                } else {
                    savedItems.forEach { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            AsyncImage(
                                model = item.imageUri,
                                contentDescription = "Gallery Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(item.description, color = Color.DarkGray)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ViewGalleryScreenPreview() {
    ViewGalleryScreen(navController = rememberNavController())
}
