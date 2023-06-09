package dev.toke.ameplus.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.AuthResponse
import dev.toke.ameplus.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepo: AuthRepository): ViewModel() {
    val authDto: MutableState<DataOrException<AuthResponse, Boolean, Exception>> = mutableStateOf(DataOrException(null, true, Exception("")))
    suspend fun signIn(userName: String): DataOrException<AuthResponse, Boolean, Exception> {
        return authRepo.signIn(userName)
    }

    fun getToken(): String? {
        return authDto.value.data?.token
    }

    fun getUser(): String? {
        return authDto.value.data?.username
    }

}