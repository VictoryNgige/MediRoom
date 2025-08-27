package com.victory.mediroom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.victory.mediroom.data.UserDatabase
import com.victory.mediroom.repository.UserRepository
import com.victory.mediroom.ui.screens.about.AboutScreen
import com.victory.mediroom.ui.screens.appointment.AppointmentScreen
import com.victory.mediroom.ui.screens.auth.LoginScreen
import com.victory.mediroom.ui.screens.auth.RegisterScreen
import com.victory.mediroom.ui.screens.cardiologist.CardiologistScreen
import com.victory.mediroom.ui.screens.dermatologist.DermatologistScreen
import com.victory.mediroom.ui.screens.familydoc.FamilydocScreen
import com.victory.mediroom.ui.screens.gallery.GalleryScreen
import com.victory.mediroom.ui.screens.gallery.ViewGalleryScreen
import com.victory.mediroom.ui.screens.home.HomeScreen
import com.victory.mediroom.ui.screens.neurologist.NeurologistScreen
import com.victory.mediroom.ui.screens.notification.NotificationScreen
import com.victory.mediroom.ui.screens.oncologist.OncologistScreen
import com.victory.mediroom.ui.screens.pediatrician.PediatricianScreen
import com.victory.mediroom.ui.screens.profile.ProfileScreen
import com.victory.mediroom.ui.screens.radiologist.RadiologistScreen
import com.victory.mediroom.ui.screens.review.ReviewScreen
import com.victory.mediroom.ui.screens.service.ServiceScreen
import com.victory.mediroom.ui.screens.splash.SplashScreen
import com.victory.mediroom.ui.screens.start.Start1Screen
import com.victory.mediroom.ui.screens.start.Start2Screen
import com.victory.mediroom.viewmodel.AuthViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }

        composable(ROUT_SERVICE) {
            ServiceScreen(navController)
        }

        composable(ROUT_START1) {
            Start1Screen(navController)
        }

        composable(ROUT_START2) {
            Start2Screen(navController)
        }

        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }

        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUT_PROFILE) {
            ProfileScreen(navController)
        }

        composable(ROUT_REVIEW) {
            ReviewScreen(navController)
        }

        composable(ROUT_APPOINTMENT) {
            AppointmentScreen(navController)
        }

        composable(ROUT_GALLERY) {
            GalleryScreen(navController)
        }
        composable(ROUT_VIEWGALLERY) {
            ViewGalleryScreen(navController)
        }

        composable(ROUT_NEUROLOGIST) {
            NeurologistScreen(navController)
        }

        composable(ROUT_CARDIOLOGIST) {
            CardiologistScreen(navController)
        }

        composable(ROUT_PEDIATRICIAN) {
            PediatricianScreen(navController)
        }

        composable(ROUT_ONCOLOGIST) {
            OncologistScreen(navController)
        }

        composable(ROUT_DERMATOLOGIST) {
            DermatologistScreen(navController)
        }

        composable(ROUT_RADIOLOGIST) {
            RadiologistScreen(navController)
        }

        composable(ROUT_FAMILYDOC) {
            FamilydocScreen(navController)
        }

        composable(ROUT_NOTIFICATION) {
            NotificationScreen(navController)
        }

        //AUTHENTICATION

        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

        //End of Authentication


    }
}