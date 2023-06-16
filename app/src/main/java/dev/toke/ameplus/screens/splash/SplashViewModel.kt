package dev.toke.ameplus.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.models.TokenResponse
import dev.toke.ameplus.repositories.DataStoreRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreRepository: DataStoreRepository) : ViewModel() {
    var token: TokenResponse? by mutableStateOf(null)

    init {
        getToken()
    }
    private fun getToken() {
        viewModelScope.launch {
            dataStoreRepository.getTokenFromDataStore().collect { it ->
                token = it
            }
        }
    }
}