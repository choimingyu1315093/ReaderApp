package com.example.readerapp.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.components.ReaderLogo
import com.example.readerapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ReaderSplashScreen(navController: NavController, modifier: Modifier = Modifier){
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(
        key1 = true
    ) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        delay(2000)
//        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
//            navController.navigate(ReaderScreens.LoginScreen.name)
//        }else {
//            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
//        }
        navController.navigate(ReaderScreens.LoginScreen.name)
    }

    Surface(
        modifier = modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(2.dp, Color.LightGray)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(1.dp)
        ) {
            ReaderLogo()
            Spacer(modifier = modifier.padding(15.dp))
            Text(
                text = "\"Read. Change. Yourself \"",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.LightGray
                )
            )
        }
    }
}

