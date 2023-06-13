package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.AuthRequest
import dev.toke.ameplus.models.AuthResult
import dev.toke.ameplus.models.TokenResponse
import dev.toke.ameplus.network.AppSharedPreferences
import dev.toke.ameplus.network.AuthApi
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi: AuthApi, private val prefs: AppSharedPreferences): AuthRepository {
//    private val authDto = DataOrException<TokenResponse, Boolean, Exception>()
    override suspend fun refreshToken(accessToken: String, refreshToken: String): AuthResult<Unit> {
        try {
            val userData = prefs.getUserData()
            val newToken = userData?.let { authApi.refresh(accessToken = it.token, refreshToken = it.refreshToken, token = "Bearer ${userData.token}") }
            if(newToken.toString().isNullOrEmpty()) {
                if (newToken != null) {
                    prefs.setAccessToken(newToken.token)
                    prefs.setUserData(newToken)
                    return AuthResult.Authorized()
                }
            }
            return AuthResult.UnknownError()
        } catch(exception: HttpException) {
            if(exception.code() == 401) {
                return AuthResult.Unauthorized()
            }
            return AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String): AuthResult<Unit> {
        try {
            Log.d("AuthRepositoryImpl", "Sign-in - start $username")
            val tokenResponse = authApi.signIn(username)
            Log.d("AuthRepositoryImpl", "Login - token received 1")
            if(tokenResponse.toString().isNotEmpty()) {
                prefs.setAccessToken(tokenResponse.token)
                prefs.setUserData(userData = tokenResponse)
            }
            Log.d("AuthRepositoryImpl", "Login - token received 2")
            return AuthResult.Authorized()
        } catch(exception: HttpException) {
            Log.d("AuthRepositoryImpl", "Login - exception - ${exception.localizedMessage}")
            if(exception.code() == 401) return AuthResult.Unauthorized()
            return AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        try {
            val token = prefs.getAccessToken() ?: return AuthResult.Unauthorized()

            Log.d("AuthRepositoryImpl", "Authenticate - completed - $token")
            return AuthResult.Authorized()
        } catch(exception: HttpException) {
            Log.d("AuthRepositoryImpl", "Authenticate - exception - ${exception.localizedMessage}")
            if(exception.code() == 401) return AuthResult.Unauthorized()
            return AuthResult.UnknownError()
        }
    }
}