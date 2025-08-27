package com.victory.mediroom.ui.screens.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.R
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.lightpurple

data class Service(val title: String, val description: String, val icon: ImageVector, val imageRes: Int, val route: String)

val services = listOf(
    Service("Book Appointment", "Schedule a doctor visit", Icons.Default.CheckCircle, R.drawable.img, "appointment"),
    Service("Online Consultation", "See a doctor virtually", Icons.Default.Info, R.drawable.img, "consult"),
    Service("Lab Reports", "View your test results", Icons.Default.Add, R.drawable.img, "reports"),
    Service("Health Packages", "Explore healthcare plans", Icons.Default.Face, R.drawable.img, "packages")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Services", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = purple)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = purple) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }, selected = true,
                    onClick = { navController.navigate("home") })
                NavigationBarItem(icon = { Icon(Icons.Default.Info, contentDescription = "Appointments") },
                    label = { Text("Appointments") }, selected = false,
                    onClick = { navController.navigate("appointment") })
                NavigationBarItem(icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }, selected = false,
                    onClick = { navController.navigate("profile") })
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(lightpurple)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(services) { service ->
                        ServiceCard(service, navController)
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Explore More Services",
                    style = MaterialTheme.typography.titleMedium,
                    color = purple,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Health Monitoring", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Text("Emergency Assistance", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Text("Wellness Programs", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }

                Spacer(Modifier.height(16.dp))

                Button(onClick = { navController.navigate("more_services") }, modifier = Modifier.fillMaxWidth()) {
                    Text("Discover All Services")
                }

                Spacer(Modifier.height(24.dp))

                // New Image-Enhanced Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_1), // placeholder image
                            contentDescription = "Virtual Consultation",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Virtual Consultations Available 24/7",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = purple,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Connect with certified doctors instantly through video calls from the comfort of your home. Reliable, fast, and secure.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.img), // second illustrative image
                    contentDescription = "Health Packages Overview",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Premium Health Packages Tailored for You",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = purple,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Explore our affordable health packages including full-body checkups, diagnostics, and preventive care—all available on your phone.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    )
}

@Composable
fun ServiceCard(service: Service, navController: NavController) { /* same as before */ }

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    ServiceScreen(navController = rememberNavController())
}
