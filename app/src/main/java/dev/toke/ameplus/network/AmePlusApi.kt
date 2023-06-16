package dev.toke.ameplus.network

import dev.toke.ameplus.dtos.SSOUser
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface AmePlusApi {

//    @Headers("Accept: application/json","Accept-Encoding: identity","Content-Type: application/json")
    @POST("Identity/SingleSignOn")
    suspend fun signIn(@Body user: SSOUser): TokenResponse

    @POST("Identity/RefreshToken")
    suspend fun refresh(@Body accessToken: String, @Body refreshToken: String, @Header("Authorization") token: String): TokenResponse

//    @GET("Parts/{partNumber}/ImagesAndNotes")
//    suspend fun getPart(@Path("partNumber") partNumber: String, @Header("Authorization") token: String): PartsData

}