package com.example.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.screens.home.Home
import com.example.readerapp.screens.login.ReaderLoginScreen
import com.example.readerapp.screens.splash.ReaderSplashScreen

@Composable
fun ReaderNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {
        composable(route = ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }

        composable(route = ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }

        composable(route = ReaderScreens.ReaderHomeScreen.name){
            Home(navController = navController)
        }
    }
}