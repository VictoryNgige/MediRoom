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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.purple1

data class Service(
    val title: String,
    val description: String,
    val icon: Int,
    val imageRes: Int,
    val route: String
)


val services = listOf(
    Service("Book Appointment", "Schedule a doctor visit", R.drawable.checkcircle, R.drawable.img_14, ""),
    Service("Virtual Consultation", "Talk to doctors 24/7", R.drawable.videocall, R.drawable.img_25, ""),
    Service("Lab Reports", "Access your test results", R.drawable.assignment, R.drawable.img_15, ""),
    Service("Health Packages", "Affordable full checkups", R.drawable.localhospital, R.drawable.img_16, ""),
    Service("Emergency Care", "24/7 ambulance & emergency", R.drawable.localphone, R.drawable.img_17, ""),
    Service("Pharmacy", "Order medicines at your door", R.drawable.medicalservices, R.drawable.img_18, "")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Our Services", fontWeight = FontWeight.Bold) },

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
                containerColor = purple1,
                titleContentColor = purple,
                navigationIconContentColor = purple
            )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = purple1) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }, selected = true,
                    onClick = { navController.navigate("home") })
                NavigationBarItem(icon = { Icon(Icons.Default.Create, contentDescription = "Reviews") },
                    label = { Text("Reviews") }, selected = false,
                    onClick = { navController.navigate("review") })
                NavigationBarItem(icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Appointments") },
                    label = { Text("Appointment") }, selected = false,
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
                // Services Scrollable Row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(services) { service ->
                        ServiceCard(service, navController)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Featured Departments
                Text(
                    text = "Featured Departments",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = purple,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val departments = listOf(
                        Pair("Cardiology", R.drawable.img_19),
                        Pair("Pediatrics", R.drawable.img_20),
                        Pair("Dermatology", R.drawable.img_21),
                        Pair("Orthopedics", R.drawable.img_22),
                        Pair("ENT", R.drawable.img_23)
                    )

                    items(departments) { (name, imageRes) ->
                        Card(
                            modifier = Modifier
                                .size(width = 160.dp, height = 120.dp)
                                .clickable { },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box {
                                Image(
                                    painter = painterResource(id = imageRes),
                                    contentDescription = name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(name, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Explore More Services
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



                Spacer(Modifier.height(24.dp))

                // Virtual Consultation Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.img_24),
                            contentDescription = "24/7 Consultation",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "24/7 Virtual Consultations",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = purple,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "No more waiting in lines. Get immediate access to qualified doctors from your home. Available day and night.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Health Packages Section
                Image(
                    painter = painterResource(id = R.drawable.img_26),
                    contentDescription = "Health Packages",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Customized Health Packages",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = purple,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "From preventive checkups to chronic disease management — explore our packages tailored for families, seniors, and professionals.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    )
}

@Composable
fun ServiceCard(service: Service, navController: NavController) {
    Card(
        modifier = Modifier
            .width(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = service.imageRes),
                contentDescription = service.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = service.icon,
                contentDescription = service.title,
                tint = purple,
                modifier = Modifier
                    .size(32.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = service.description,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

private fun ColumnScope.Icon(
    imageVector: Int,
    contentDescription: String,
    tint: Color,
    modifier: Modifier
) {
}

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    ServiceScreen(navController = rememberNavController())
}
