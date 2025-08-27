package com.victory.mediroom.ui.screens.gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.victory.mediroom.R
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.purple
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// --- ROOM ENTITY ---
@Entity(tableName = "gallery_item")
data class GalleryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val imageUri: String
)

// --- DAO ---
@Dao
interface GalleryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GalleryItem)

    @Query("SELECT * FROM gallery_item ORDER BY id DESC")
    fun getAllItems(): Flow<List<GalleryItem>>
}

// --- DATABASE ---
@Database(entities = [GalleryItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "gallery_db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}

// --- VIEWMODEL ---
class GalleryViewModel(private val dao: GalleryDao) : ViewModel() {

    private val _items = MutableStateFlow<List<GalleryItem>>(emptyList())
    val items: StateFlow<List<GalleryItem>> = _items.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllItems().collect {
                _items.value = it
            }
        }
    }

    fun addItem(title: String, description: String, imageUri: String) {
        viewModelScope.launch {
            dao.insert(GalleryItem(title = title, description = description, imageUri = imageUri))
        }
    }

    class Factory(private val dao: GalleryDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GalleryViewModel(dao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

// Helper function to decode bitmap safely
private fun decodeBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// --- MAIN COMPOSABLE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val viewModel: GalleryViewModel = viewModel(factory = GalleryViewModel.Factory(db.galleryDao()))
    val galleryItems by viewModel.items.collectAsState()

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gallery Admin") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Add sharing or other actions */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* TODO: Notifications */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purple,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = purple) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0; navController.navigate("home_route") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Create, contentDescription = "Reviews") },
                    label = { Text("Reviews") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1; navController.navigate("reviews_route") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Appointment") },
                    label = { Text("Appointment") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2; navController.navigate("appointment_route") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3; navController.navigate("profile_route") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("gallery_route") }, containerColor = purple) {
                Icon(Icons.Default.Face, contentDescription = "Gallery")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(lightpurple)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                Text("Add New Gallery Item", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select Image")
                }

                Spacer(modifier = Modifier.height(8.dp))

                selectedImageUri?.let { uri ->
                    val bitmap by remember(uri) {
                        mutableStateOf(decodeBitmapFromUri(context, uri))
                    }
                    bitmap?.let { bmp ->
                        Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    imagePickerLauncher.launch("image/*")
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.text.isNotBlank() && description.text.isNotBlank() && selectedImageUri != null) {
                            viewModel.addItem(title.text, description.text, selectedImageUri.toString())
                            title = TextFieldValue("")
                            description = TextFieldValue("")
                            selectedImageUri = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.text.isNotBlank() && description.text.isNotBlank() && selectedImageUri != null
                ) {
                    Text("Upload")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Divider()

                Spacer(modifier = Modifier.height(16.dp))

                Text("Uploaded Gallery Items", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                if (galleryItems.isEmpty()) {
                    Text("No items uploaded yet.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                } else {
                    LazyColumn {
                        items(galleryItems) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                ) {
                                    val imageUri = remember(item.imageUri) { Uri.parse(item.imageUri) }
                                    val bitmap = remember(imageUri) { decodeBitmapFromUri(context, imageUri) }

                                    if (bitmap != null) {
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = item.title,
                                            modifier = Modifier.size(80.dp)
                                        )
                                    } else {
                                        // Fallback placeholder image in case of error
                                        Image(
                                            painter = painterResource(id = R.drawable.img),
                                            contentDescription = "Placeholder",
                                            modifier = Modifier.size(80.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(item.title, style = MaterialTheme.typography.titleMedium)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(item.description, style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    GalleryScreen(navController = rememberNavController())
}
