package com.victory.mediroom.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_APPOINTMENT
import com.victory.mediroom.navigation.ROUT_GALLERY
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_GALLERY
import com.victory.mediroom.navigation.ROUT_PROFILE
import com.victory.mediroom.navigation.ROUT_REVIEW
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController){

        //Scaffold

        var selectedIndex by remember { mutableStateOf(0) }

        Scaffold(
            //TopBar

            topBar = {
                TopAppBar(
                    title = { Text("MediRoom", fontWeight = FontWeight.Bold) },
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



            //BottomBar
            bottomBar = {
                NavigationBar(
                    containerColor = purple1
                ){
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0
                            navController.navigate(ROUT_HOME)
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Create, contentDescription = "Favorites") },
                        label = { Text("Reveiws") },
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1
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
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2
                            navController.navigate(ROUT_PROFILE)
                        }
                    )


                }
            },


            //FloatingActionButton
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(ROUT_GALLERY) },
                    containerColor = purple
                ) {
                    Icon(Icons.Default.Face, contentDescription = "gallery")
                }
            },


            //Content
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(color = lightpurple2)
                        .verticalScroll(rememberScrollState())
                ) {


                    //Main Contents of the page

                    // Hospital Image Banner
                    Image(
                        painter = painterResource(id = R.drawable.hospital_logo), // Replace with actual image
                        contentDescription = "Hospital Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

// Welcome Text
                    Text(
                        text = "Welcome to MediRoom Hospital",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = purple,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )

// Subheading
                    Text(
                        text = "Your health, our priority",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

// Description Card
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = lightpurple)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "About Us",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = purple
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "MediRoom is a modern, patient-centric hospital dedicated to delivering exceptional healthcare. With state-of-the-art facilities, a team of expert doctors, and easy-to-use digital services, we ensure a seamless medical experience — from booking appointments to accessing care.",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

// Key Features
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "What You Can Do",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = purple
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("• Book appointments easily from anywhere.")
                            Text("• Explore doctor profiles and specialties.")
                            Text("• View hospital services and departments.")
                            Text("• Get notified about health tips & updates.")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

// Principles Section
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = lightpurple)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Our Core Principles",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = purple
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("✅ Compassion: We treat every patient with kindness and dignity.")
                            Text("✅ Excellence: We aim for the highest standards in medical care.")
                            Text("✅ Innovation: We embrace technology to enhance patient experience.")
                            Text("✅ Integrity: We value honesty, ethics, and transparency.")
                            Text("✅ Collaboration: We work together for the best outcomes.")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

// Footer
                    Text(
                        text = "Thank you for choosing MediRoom.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 32.dp)
                    )








                }
            }
        )

        //End of scaffold

}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    AboutScreen(navController= rememberNavController())
}