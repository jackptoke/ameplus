package dev.toke.ameplus.network

import dev.toke.ameplus.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthApi {
    @POST("Identity/SingleSignOn")
    suspend fun signIn(@Body userName: String): TokenResponse

    @POST("Identity/RefreshToken")
    suspend fun refresh(@Body accessToken: String, @Body refreshToken: String, @Header("Authorization") token: String): TokenResponse

}