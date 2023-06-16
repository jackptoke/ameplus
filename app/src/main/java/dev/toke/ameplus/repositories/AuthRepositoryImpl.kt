package dev.toke.ameplus.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import dev.toke.ameplus.dtos.SSOUser
import dev.toke.ameplus.data.AuthResult
import dev.toke.ameplus.models.TokenResponse
import dev.toke.ameplus.network.AmePlusApi
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authApi: AmePlusApi, private val dataStoreRepository: DataStoreRepository): AuthRepository {
    val accessToken: LiveData<TokenResponse?>
        get() = dataStoreRepository.validAccessToken

    override suspend fun refreshToken(accessToken: String, refreshToken: String): AuthResult<TokenResponse?> {
        try {
            val token = dataStoreRepository.getTokenFromDataStore()
            // TODO
            return AuthResult.UnknownError()
        } catch(exception: HttpException) {
            if(exception.code() == 401) {
                return AuthResult.Unauthorized()
            }
            return AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String): AuthResult<TokenResponse?> {
        try {
            Log.d("AuthRepositoryImpl", "Sign-in - start $username")
            val tokenResponse = authApi.signIn(SSOUser(userName = username))

            Log.d("AuthRepositoryImpl", "Login - token received 1 ${ tokenResponse.toString() }")
            if(tokenResponse.toString().isNotEmpty()) {
                dataStoreRepository.saveTokenToDataStore(tokenResponse)
                Log.d("AuthRepositoryImpl", "Login - authorized")
                return AuthResult.Authorized(data = tokenResponse)
            }
            Log.d("AuthRepositoryImpl", "Login - unauthorized")
            return AuthResult.Unauthorized()
        } catch(exception: Exception) {
            Log.d("AuthRepositoryImpl", "Login - exception - ${exception.localizedMessage}")

            when(exception) {
                is HttpException -> {
                    if(exception.code() == 401) return AuthResult.Unauthorized()
                }
                else -> {}
            }
            return AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<TokenResponse?> {
        try {
            lateinit var result: AuthResult<TokenResponse?>
            Log.d("AuthRepositoryImpl", "Authenticate - before getting token from data store")
            dataStoreRepository.getTokenFromDataStore().collect {
                Log.d("AuthRepositoryImpl", "Authenticate - token")
                result = if(it == null) AuthResult.Unauthorized() else AuthResult.Authorized(data = it)
            }
            Log.d("AuthRepositoryImpl", "Authenticate - after getting token from data store")
            return result
        }
        catch(exception: Exception) {
            Log.d("AuthRepositoryImpl", "Authenticate - exception - ${exception.localizedMessage}")
            return AuthResult.UnknownError()
        }
    }

    override suspend fun signOut(): AuthResult<TokenResponse?> {
        return try {
            dataStoreRepository.deleteTokenFromDataStore()
            AuthResult.Unauthorized()
        } catch (exception: Exception) {
            exception.localizedMessage?.let { Log.d("AuthRepositoryImpl", it) }
            AuthResult.UnknownError()
        }

    }
}