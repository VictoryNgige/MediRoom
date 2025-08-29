package com.victory.mediroom.ui.screens.appointment

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.victory.mediroom.R
import com.victory.mediroom.navigation.*
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

data class DoctorInfo(val name: String, val specialty: String, val imageRes: Int, val availableTimes: List<String> )

@Entity(tableName = "appointment")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val doctorName: String,
    val time: String,
    val notes: String
)

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(a: Appointment)
    @Query("SELECT * FROM appointment ORDER BY id DESC")
    fun getAll(): Flow<List<Appointment>>
}

@Database(entities = [Appointment::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "appt_db")
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
        }
    }
}

class AppointmentViewModel(private val dao: AppointmentDao): ViewModel() {
    val allAppointments: StateFlow<List<Appointment>> =
        dao.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    fun saveAppointment(appointment: Appointment) = viewModelScope.launch { dao.insert(appointment) }
    class Factory(val dao: AppointmentDao): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(c: Class<T>): T {
            return AppointmentViewModel(dao) as T
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    navController: NavController,
    viewModel: AppointmentViewModel = viewModel(
        factory = AppointmentViewModel.Factory(AppDatabase.getDatabase(LocalContext.current).appointmentDao())
    )
) {
    val doctors = remember {
        listOf(
            DoctorInfo("Dr. Jane Smith", "Neurologist", R.drawable.neuro1, listOf("09:00 AM", "11:00 AM"), ),
            DoctorInfo("Dr. Michael Lee", "Cardiologist", R.drawable.neuro3, listOf("10:30 AM", "01:00 PM")),
            DoctorInfo("Dr. Aisha Patel", "Dermatologist", R.drawable.neuro2, listOf("08:00 AM", "12:00 PM")),
            DoctorInfo("Dr. Aisha Patel", "Dermatologist", R.drawable.neuro2, listOf("08:00 AM", "12:00 PM")),
            DoctorInfo("Dr. Aisha Patel", "Dermatologist", R.drawable.neuro2, listOf("08:00 AM", "12:00 PM")),
        )
    }
    var selectedDoctor by remember { mutableStateOf<DoctorInfo?>(null) }
    var selectedTime by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf(TextFieldValue("")) }
    var showForm by rememberSaveable { mutableStateOf(false) }

    val pastAppt by viewModel.allAppointments.collectAsState()

    Scaffold(
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
                var selectedIndex: Int? = null
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



        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Add, contentDescription = "Form") },
                text = { Text(if (showForm) "Close Form" else "Book Appointment") },
                onClick = { showForm = !showForm },
                containerColor = purple,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        },
        floatingActionButtonPosition = FabPosition.End,
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
                doctors.forEach { doc ->
                    AppointmentCard(doc) { selectedDoctor = doc }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (showForm && selectedDoctor != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Time", fontWeight = FontWeight.SemiBold)
                    selectedDoctor!!.availableTimes.forEach { t ->
                        Button(
                            onClick = { selectedTime = t },
                            colors = ButtonDefaults.buttonColors(containerColor = if (selectedTime == t) purple else Color.LightGray),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) { Text(t, color = if (selectedTime == t) Color.White else Color.Black) }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notes, onValueChange = { notes = it },
                        label = { Text("Notes") }, modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            viewModel.saveAppointment(Appointment(doctorName = selectedDoctor!!.name, time = selectedTime, notes = notes.text))
                            showForm = false; notes = TextFieldValue(""); selectedTime = ""
                        },
                        enabled = selectedTime.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit")
                    }
                }
                if (pastAppt.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Past Appointments", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    pastAppt.forEach {
                        Text("${it.doctorName} at ${it.time}")
                    }
                }
            }
        }
    )
}

@Composable
fun AppointmentCard(doctor: DoctorInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(doctor.imageRes), contentDescription = null,
                modifier = Modifier.size(56.dp).clip(CircleShape), contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = purple)
                Text(doctor.specialty, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentScreen() {
    AppointmentScreen(navController = rememberNavController())
}
