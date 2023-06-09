package dev.toke.ameplus.network

import dev.toke.ameplus.models.Part
import dev.toke.ameplus.models.PartsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface AMEPartsApi {
    @GET("Parts/{partNumber}/ImagesAndNotes")
    suspend fun getPart(@Path("partNumber") partNumber: String): PartsData
}