package dev.toke.ameplus.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.models.AuthResult
import dev.toke.ameplus.models.AuthState
import dev.toke.ameplus.repositories.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepo: AuthRepository): ViewModel() {
    var state by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
                Log.d("LoginViewModel", "OnEvent - username changed")
            }
            is AuthUiEvent.SignIn -> {
                Log.d("LoginViewModel", "OnEvent - signing in")
                signIn()

            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            Log.d("LoginViewModel", "SignIn started")
            state = state.copy(isLoading = true)
            Log.d("LoginViewModel", "Loading state copied for ${ state.signInUsername }")
            val result = authRepo.signIn(state.signInUsername)
            Log.d("LoginViewModel", "SignIn result received ${ result.data.toString() }")
            resultChannel.send(result)
            state = state.copy(isLoading = false)
            Log.d("LoginViewModel", "SignIn completed")
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepo.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
            Log.d("LoginViewModel", "Authenticate launched")
        }
    }

}