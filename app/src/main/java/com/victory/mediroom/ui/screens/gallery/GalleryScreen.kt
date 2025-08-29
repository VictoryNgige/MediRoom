package com.victory.mediroom.ui.screens.gallery

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import coil.compose.AsyncImage
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_HOME
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Entity
@Entity(tableName = "gallery_items")
data class GalleryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String,
    val description: String
)

// DAO
@Dao
interface GalleryDao {
    @Insert
    suspend fun insert(item: GalleryItem)

    @Query("SELECT * FROM gallery_items ORDER BY id DESC")
    fun getAll(): Flow<List<GalleryItem>>
}

// Room DB
@Database(entities = [GalleryItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun galleryDao(): GalleryDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gallery_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// ViewModel
class GalleryViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).galleryDao()

    val galleryItems: StateFlow<List<GalleryItem>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addGalleryItem(uri: String, description: String) {
        viewModelScope.launch {
            dao.insert(GalleryItem(imageUri = uri, description = description))
        }
    }
}

// ViewModel Factory
class GalleryViewModelFactory(private val app: Application) :
    ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryViewModel(app) as T
    }
}

// Composable Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(navController: NavController) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current

    // ViewModel - safe during preview
    val viewModel: GalleryViewModel? = if (!isPreview) {
        val app = context.applicationContext as? Application
        requireNotNull(app) { "Application context is null or invalid." }

        viewModel(factory = GalleryViewModelFactory(app))
    } else null

    // Safe collection of gallery items
    val savedItems by viewModel?.galleryItems?.collectAsState() ?: remember { mutableStateOf(emptyList()) }

    // State for new image entries
    val imageEntryList = remember { mutableStateMapOf<Uri, String>() }

    // Image picker launcher
    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            imageEntryList[it] = ""
        }
    }

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Face, contentDescription = "Gallery")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Gallery List
                Text("Saved Gallery", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))

                if (savedItems.isEmpty()) {
                    Text("No items in gallery.", color = Color.Gray)
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
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(item.description)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Add New Image", style = MaterialTheme.typography.titleMedium)

                Button(onClick = {
                    pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                }) {
                    Icon(painter = painterResource(id = R.drawable.addaphoto), contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Pick an Image")
                }

                Spacer(modifier = Modifier.height(16.dp))

                imageEntryList.forEach { (uri, desc) ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        BasicTextField(
                            value = desc,
                            onValueChange = { imageEntryList[uri] = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, RoundedCornerShape(4.dp))
                                .padding(8.dp),
                            decorationBox = { inner ->
                                if (desc.isEmpty()) Text("Add description...", color = Color.Gray)
                                inner()
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                viewModel?.addGalleryItem(uri.toString(), desc)
                                imageEntryList.remove(uri)
                            },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Save to Gallery")
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
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
