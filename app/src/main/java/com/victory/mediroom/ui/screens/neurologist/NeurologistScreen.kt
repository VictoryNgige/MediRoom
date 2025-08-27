package com.victory.mediroom.ui.screens.neurologist


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.victory.mediroom.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun NeurologistScreen(navController: NavController){

    //Scaffold

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        //TopBar
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
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )

                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )

                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightpurple2,
                    titleContentColor = purple,
                    navigationIconContentColor = purple
                )
            )
        },

        //BottomBar
        bottomBar = {
            NavigationBar(
                containerColor = lightpurple2
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
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Products") },
                    label = { Text("Appointment") },
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3
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
                    .background(color = lightpurple)
                    .verticalScroll(rememberScrollState())
            ) {


                //Main Contents of the page

                //Card
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.neuro1),
                            contentDescription = "neuro1",
                            modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "Dr.Victory",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            color = purple

                        )
                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Book Appointment", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 70.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("View Profile", color = Color.White)
                            }
                        }


                    }
                }
                //End of Card

                Spacer(modifier = Modifier.height(10.dp))

                //Card
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.neuro2),
                            contentDescription = "neuro2",
                            modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "Dr.Blake",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            color = purple

                        )
                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Book Appointment", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 70.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("View Profile", color = Color.White)
                            }
                        }


                    }
                }
                //End of Card

                Spacer(modifier = Modifier.height(10.dp))

                //Card
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.neuro3),
                            contentDescription = "neuro3",
                            modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "Dr.Grey",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            color = purple

                        )
                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Book Appointment", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 70.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("View Profile", color = Color.White)
                            }
                        }


                    }
                }
                //End of Card

                Spacer(modifier = Modifier.height(10.dp))

                //Card
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.neuro4),
                            contentDescription = "neuro4",
                            modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "Dr.Prisca",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            color = purple

                        )
                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Book Appointment", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 70.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("View Profile", color = Color.White)
                            }
                        }


                    }
                }
                //End of Card

                Spacer(modifier = Modifier.height(10.dp))

                //Card
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(start = 10.dp, end = 10.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.neuro5),
                            contentDescription = "neuro5",
                            modifier = Modifier.fillMaxSize().clip(shape = RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.FillBounds
                        )
                        Text(
                            text = "Dr.Ngige",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .padding(start = 10.dp),
                            color = purple

                        )
                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 10.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Book Appointment", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .padding(top = 70.dp)
                                .height(50.dp)
                                .background(
                                    color = purple,
                                    shape = RoundedCornerShape(12.dp)

                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {

                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("View Profile", color = Color.White)
                            }
                        }


                    }
                }
                //End of Card

            }
        }
    )

    //End of scaffold

}

@Preview(showBackground = true)
@Composable
fun NeurologistScreenPreview(){
    NeurologistScreen(navController= rememberNavController())
}