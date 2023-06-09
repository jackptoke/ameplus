package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.AuthResponse
import dev.toke.ameplus.network.AuthApi
import dev.toke.ameplus.network.SessionManager
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi: AuthApi, private val sessionManager: SessionManager): AuthRepository {
    private val authDto = DataOrException<AuthResponse, Boolean, Exception>()
    override suspend fun refreshToken(refreshToken: String): String {
        TODO("Not yet implemented")
        return "new_token"
    }

    override suspend fun signIn(username: String): DataOrException<AuthResponse, Boolean, Exception> {
        try {
            authDto.loading = true
            authDto.data = authApi.signIn(username)

            if(authDto.data.toString().isNotEmpty()) authDto.loading = false
            Log.d("AuthRepositoryImpl", "Login - token - ${(authDto.data as AuthResponse).token}")
        } catch(exception: Exception) {
            authDto.exception = exception
            Log.d("AuthRepositoryImpl", "Login - exception - ${exception.localizedMessage}")
        }
        return  authDto
    }
}