package com.victory.mediroom.ui.screens.profile

import android.R.attr.label
import android.R.attr.password
import android.R.attr.shape
import android.R.attr.textStyle
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_ABOUT
import com.victory.mediroom.navigation.ROUT_APPOINTMENT
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_PROFILE
import com.victory.mediroom.navigation.ROUT_REVIEW
import com.victory.mediroom.ui.theme.lightpurple2
import com.victory.mediroom.ui.theme.purple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

// region ROOM DATABASE SETUP
@Entity(tableName = "user_profile")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val name: String,
    val username: String, // New field for username
    val email: String,
    val gender: String, // New field for gender
    val bio: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val profileImage: ByteArray? // New field for profile image
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (name != other.name) return false
        if (username != other.username) return false
        if (email != other.email) return false
        if (gender != other.gender) return false
        if (bio != other.bio) return false
        if (profileImage != null) {
            if (other.profileImage == null) return false
            if (!profileImage.contentEquals(other.profileImage)) return false
        } else if (other.profileImage != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + bio.hashCode()
        result = 31 * result + (profileImage?.contentHashCode() ?: 0)
        return result
    }
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUser(): Flow<User?>
}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "profile_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// region VIEWMODEL
class ProfileViewModel(private val userDao: UserDao) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        viewModelScope.launch {
            userDao.getUser().collect { userProfile ->
                _user.value = userProfile
            }
        }
    }

    fun saveProfile(name: String, username: String, email: String, gender: String, bio: String, profileImage: ByteArray?) {
        viewModelScope.launch {
            val userProfile = User(name = name, username = username, email = email, gender = gender, bio = bio, profileImage = profileImage)
            userDao.insertUser(userProfile)
        }
    }

    class Factory(private val userDao: UserDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
// endregion VIEWMODEL

// region COMPOSE UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory(db.userDao()))

    val userProfile by viewModel.user.collectAsState()

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf<ByteArray?>(null) }

    // State for the gender dropdown menu
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf(
        "Male",
        "Female",
        "Non-binary",
        "Transgender",
        "Intersex",
        "Two-Spirit",
        "Genderqueer",
        "Prefer not to say"
    )

    // LaunchedEffect to update the form fields when the userProfile data changes
    LaunchedEffect(userProfile) {
        userProfile?.let {
            name = it.name
            username = it.username
            email = it.email
            gender = it.gender
            bio = it.bio
            profileImage = it.profileImage
        }
    }

    // Activity Result Launcher for picking an image
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            profileImage = outputStream.toByteArray()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightpurple2,
                    titleContentColor = purple
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = purple
                        )
                    }
                }
            )
        },

        // Bottom Navigation Bar
        bottomBar = {
            NavigationBar(containerColor = lightpurple2) {
                var selectedIndex: Int? = null
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


        ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Profile Photo Section
                val bitmap = profileImage?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                Image(
                    bitmap = bitmap?.asImageBitmap() ?: run {
                        remember { Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).asImageBitmap() }
                    },
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        }
                )
                Text("Tap to change photo", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }


            item {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }



            item {
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Button(
                    onClick = {
                        viewModel.saveProfile(name, username, email, gender, bio, profileImage)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = purple),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save Profile", color = Color.White)
                }
            }
        }
    }
}
// endregion COMPOSE UI

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}