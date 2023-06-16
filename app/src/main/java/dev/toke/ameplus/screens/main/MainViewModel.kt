package dev.toke.ameplus.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.data.AuthResult
import dev.toke.ameplus.repositories.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepo: AuthRepository): ViewModel()  {

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()

    fun onEvent(event: MainUiEvent) {
        when(event) {
            is MainUiEvent.SignOutClicked -> {
                signOut()
            }

            else -> {}
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepo.signOut()
            resultChannel.send(AuthResult.Unauthorized())
            Log.d("LoginViewModel", "Sign-out")
        }
    }
}