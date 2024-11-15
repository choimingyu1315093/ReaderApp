package com.example.readerapp.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel(){
//    val loadingState = MutableStateFlow(LoadingState.IDLE)

    private val auth: FirebaseAuth = Firebase.auth

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Log.d("TAG", "signInWithEmailAndPassword: ${task.result.toString()}")
                            home()
                        }else {
                            Log.d("TAG", "signInWithEmailAndPassword: ${task.result.toString()}")
                        }
                    }
            }catch(exception: Exception){
                Log.d("TAG", "signInWithEmailAndPassword: ${exception.message}")
            }
        }
    }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("TAG", "createUserWithEmailAndPassword: ${task.result.toString()}")
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)
                        home()
                    }else {
                        Log.d("TAG", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer",
            id = null
        ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}