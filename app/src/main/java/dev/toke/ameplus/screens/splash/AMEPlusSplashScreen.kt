package dev.toke.ameplus.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.toke.ameplus.R
import dev.toke.ameplus.navigation.AMEPlusScreens
import kotlinx.coroutines.delay

@Composable
fun AMEPlusSplashScreen(navController: NavController, viewModel: SplashViewModel = hiltViewModel()) {
    val scale = remember{ Animatable(0f) }
    LaunchedEffect(key1 = true, block = {

        scale.animateTo(targetValue = 0.9f, animationSpec = tween(
            durationMillis = 800,
            easing = { OvershootInterpolator(8f).getInterpolation(it) }
        ))
        delay(2000L)
        if(viewModel.token == null)
            navController.navigate(AMEPlusScreens.LoginScreen.name)
        else navController.navigate(AMEPlusScreens.AMEPlusMainScreen.name)
    })
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(modifier = Modifier
            .padding(15.dp)
            .size(230.dp)
            .background(Color.Transparent)
            .scale(scale.value),
            shape = CircleShape,
            border = BorderStroke(width = 3.dp, color = Color.LightGray,
                )
        ) {
            Column(
                modifier = Modifier.padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ameplus),
                    contentDescription = "AME Plus Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.width(135.dp)
                )
                Text(
                    "Efficiency is our priority", style = MaterialTheme.typography.titleSmall,
                    color = Color.LightGray, modifier = Modifier.padding(6.dp)
                )
            }
        }

    }
}