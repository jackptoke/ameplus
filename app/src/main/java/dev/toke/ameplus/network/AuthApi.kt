package dev.toke.ameplus.network

import dev.toke.ameplus.models.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApi {
    @POST("Identity/SingleSignOn")
    suspend fun signIn(@Body userName: String): AuthResponse

    @POST("Identity/RefreshToken")
    suspend fun refresh(@Body userName: String): AuthResponse
}