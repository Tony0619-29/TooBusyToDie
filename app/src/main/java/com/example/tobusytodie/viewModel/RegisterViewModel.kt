package com.example.tobusytodie.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    //publisher para el loader

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState

    //publisher para el registro
    private val _validRegister = MutableLiveData<Boolean>()
    val validRegister: LiveData<Boolean>
        get() = _validRegister

    private val firebase = FirebaseAuth.getInstance()

    //funcion para registrar
    fun requestSignUp(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _loaderState.value = true

            viewModelScope.launch {
                val result = firebase.createUserWithEmailAndPassword(email, password).await()
                _loaderState.value = false

                result.user?.let {
                    delay(5000)
                    Log.i("Firebase", "Se creó al usuario con éxito.")
                    _validRegister.value = true
                } ?: run {
                    delay(5000)
                    Log.e("Firebase", "Ocurrio un problema al crear al usuario.")

                }
            }
        } else {
            _validRegister.value = false
        }
    }



}