package com.example.readerapp.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.readerapp.components.ReaderLogo
import com.example.readerapp.components.UserForm
import com.example.readerapp.navigation.ReaderScreens

@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel(),
    modifier: Modifier = Modifier){
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface (
        modifier = modifier
            .fillMaxSize()
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            ReaderLogo()
            if(showLoginForm.value){
                UserForm(
                    loading = false,
                    isCreateAccount = false,
                    onDone = { email, password ->
                        Log.d("TAG", "ReaderLoginScreen: $email, $password")
                        viewModel.signInWithEmailAndPassword(email, password, home = {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        })
                    }
                )
            }else {
                UserForm(
                    loading = false,
                    isCreateAccount = true,
                    onDone = { email, password ->
                        Log.d("TAG", "ReaderLoginScreen: $email, $password")
                        viewModel.createUserWithEmailAndPassword(email, password, home = {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        })
                    }
                )
            }
        }

        Spacer(modifier = modifier.height(15.dp))
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(15.dp)
        ){
            val text = if(showLoginForm.value) "Sign up" else "Login"
            Text(
                text = "New User?"
            )
            Text(
                text = text,
                modifier = modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
