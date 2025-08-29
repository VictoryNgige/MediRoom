package com.victory.mediroom.ui.screens.appointment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.navigation.ROUT_APPOINTMENT
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_PROFILE
import com.victory.mediroom.navigation.ROUT_REVIEW
import com.victory.mediroom.ui.screens.about.AboutScreen
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAppointmentScreen(navController: NavController) {

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


        content = { paddingValues ->

            // Get the ViewModel and appointments
            val viewModel: AppointmentViewModel = viewModel(
                factory = AppointmentViewModel.Factory(
                    AppDatabase.getDatabase(LocalContext.current).appointmentDao()
                )
            )
            val appointments by viewModel.allAppointments.collectAsState()

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = lightpurple2)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text("Your Appointments", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                if (appointments.isEmpty()) {
                    Text("No appointments booked yet.")
                } else {
                    appointments.forEach { appointment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Doctor: ${appointment.doctorName}", fontWeight = FontWeight.Bold, color = purple)
                                Text("Time: ${appointment.time}")
                                if (appointment.notes.isNotBlank()) {
                                    Text("Notes: ${appointment.notes}")
                                }
                            }
                        }
                    }
                }
            }
        }


    )
            //End of Scaffold





}





@Preview(showBackground = true)
@Composable
fun ViewAppointmentScreenPreview(){
    ViewAppointmentScreen(navController = rememberNavController())
}