package com.victory.mediroom.ui.screens.splash

import android.annotation.SuppressLint
import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperties
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.victory.mediroom.R
import com.victory.mediroom.navigation.ROUT_HOME
import com.victory.mediroom.navigation.ROUT_LOGIN
import com.victory.mediroom.navigation.ROUT_START1
import com.victory.mediroom.ui.theme.lightblue
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.lightpurple
import com.victory.mediroom.ui.theme.newblue
import com.victory.mediroom.ui.theme.newblue2
import com.victory.mediroom.ui.theme.purple
import com.victory.mediroom.ui.theme.purple1
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    scope.launch {
        delay(3000)  // ~2.5 sec splash duration
        navController.navigate(ROUT_START1)
    }

    // Scale animation for logo
    val scale = remember { Animatable(0.7f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing)
        )
    }

    fun LottieAnimation(
        composition: LottieComposition?,
        progress: () -> Float,
        modifier: Modifier,
        dynamicProperties: LottieDynamicProperties
    ) {
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purple1),  // clean clinical background
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(R.drawable.hospital_logo),  // Hospital logo
                contentDescription = "Hospital Logo",
                modifier = Modifier
                    .size(180.dp)
                    .scale(scale.value)
            )


            Spacer(Modifier.height(12.dp))
            Text(
                "Your Health, Our Priority",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = purple
            )
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .width(160.dp)
                    .height(4.dp),
                color = purple,
                trackColor = purple.copy(alpha = 0.3f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){

    SplashScreen(rememberNavController())

}