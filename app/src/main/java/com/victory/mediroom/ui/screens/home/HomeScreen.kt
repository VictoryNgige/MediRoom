package com.victory.mediroom.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.victory.mediroom.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.navigation.ROUT_ABOUT
import com.victory.mediroom.navigation.ROUT_APPOINTMENT
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_LOGIN
import com.victory.mediroom.navigation.ROUT_PROFILE
import com.victory.mediroom.navigation.ROUT_REVIEW
import com.victory.mediroom.navigation.ROUT_SERVICE
import com.victory.mediroom.navigation.ROUT_VIEWGALLERY
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MediRoom", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ROUT_LOGIN) }) {
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
                val navItems = listOf(
                    Pair("Home", Icons.Default.Home to ROUT_HOME),
                    Pair("Reviews", Icons.Default.Create to ROUT_REVIEW),
                    Pair("Appointment", Icons.Default.CheckCircle to ROUT_APPOINTMENT),
                    Pair("Profile", Icons.Default.Person to ROUT_PROFILE)
                )

                navItems.forEachIndexed { idx, pair ->
                    val (label, iconNav) = pair
                    NavigationBarItem(
                        icon = { Icon(iconNav.first, contentDescription = label) },
                        label = { Text(label) },
                        selected = selectedIndex == idx,
                        onClick = {
                            selectedIndex = idx
                            navController.navigate(iconNav.second)
                        }
                    )
                }
            }
        },

        containerColor = lightpurple
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    lightpurple2
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Greeting Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Good Morning,",
                        fontSize = 24.sp,
                        color = purple
                    )
                    Text(
                        text = "Dr. Smith", // Or dynamically fetched user name
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = purple
                    )
                }
                Image(
                    painter = painterResource(R.drawable.hospital_logo),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Info Cards Row
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCardWithCarousel(
                    title = "About Us",
                    icon = Icons.Default.AccountCircle,
                    images = listOf(R.drawable.img_2, R.drawable.img_3, R.drawable.img_4)
                ) {
                    navController.navigate(ROUT_ABOUT)
                }
            }


            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCardWithCarousel(
                    title = "Gallery",
                    icon = Icons.Default.Face,
                    images = listOf(R.drawable.img_5, R.drawable.img_6, R.drawable.img_7)
                ) {
                    navController.navigate(ROUT_VIEWGALLERY)
                }
            }


            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCardWithCarousel(
                    title = "Services",
                    icon = Icons.Default.Build,
                    images = listOf(R.drawable.img_8, R.drawable.img_9, R.drawable.img_10)
                ) {
                    navController.navigate(ROUT_SERVICE)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashboardCardWithCarousel(
                    title = "Appointments",
                    icon = Icons.Default.CheckCircle,
                    images = listOf(R.drawable.img_11, R.drawable.img_12, R.drawable.img_13)
                ) {
                    navController.navigate(ROUT_APPOINTMENT)
                }
            }


            // Slogan
            Text(
                text = "Serving with a Heart of Gold",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DashboardCardWithCarousel(
    title: String,
    icon: ImageVector,
    images: List<Int>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {

    Box(modifier = Modifier.fillMaxSize()) {
        AutoScrollingImageRow(
            images = images,
            modifier = Modifier.fillMaxSize() // full background
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                    .padding(4.dp)

            )


            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )


        }

    }


    }
}

@Composable
fun AutoScrollingImageRow(
    images: List<Int>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(images) {
        if (images.isEmpty()) return@LaunchedEffect
        while (true) {
            coroutineScope.launch {
                val next = (listState.firstVisibleItemIndex + 1) % images.size
                listState.animateScrollToItem(next)
            }
            delay(3000L)
        }
    }
    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(images) { img ->
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 330.dp, height = 140.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
