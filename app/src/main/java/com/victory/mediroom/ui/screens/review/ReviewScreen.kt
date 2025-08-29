package com.victory.mediroom.ui.screens.review

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.victory.mediroom.navigation.*
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ----------- Room Database Setup --------------

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String
)

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(review: Review)

    @Query("SELECT * FROM reviews ORDER BY id DESC")
    fun getAllReviews(): LiveData<List<Review>>
}

@Database(entities = [Review::class], version = 1)
abstract class ReviewDatabase : RoomDatabase() {
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        fun getDatabase(context: android.content.Context): ReviewDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java,
                    "review_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ReviewDatabase.getDatabase(application).reviewDao()

    // LiveData to observe all reviews from the database
    val allReviews: LiveData<List<Review>> = dao.getAllReviews()

    // Function to insert a new review into the database
    fun submitReview(content: String, onSuccess: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(Review(content = content))
            onSuccess()
        }
    }
}


// ----------- UI Screen -------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavController) {
    val context = LocalContext.current
    val reviewViewModel: ReviewViewModel = viewModel(
        factory = object : ViewModelProvider.AndroidViewModelFactory(
            context.applicationContext as Application
        ) {}
    )

    var selectedIndex by remember { mutableStateOf(1) }
    var reviewText by remember { mutableStateOf("") }
    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mediroom") },
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

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUT_GALLERY) },
                containerColor = purple
            ) {
                Icon(Icons.Default.Face, contentDescription = "gallery")
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(lightpurple)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Text(
                    text = "We value your feedback!",
                    color = purple,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Tell us about your experience:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Your Review") },
                    placeholder = { Text("Type your experience here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        if (reviewText.isNotBlank()) {
                            reviewViewModel.submitReview(reviewText.trim()) {
                                reviewText = ""
                                showSuccess = true
                            }
                        } else {
                            Toast.makeText(context, "Review cannot be empty.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Submit")
                }

                if (showSuccess) {
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                        showSuccess = false
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ReviewScreen(navController = rememberNavController())
}
