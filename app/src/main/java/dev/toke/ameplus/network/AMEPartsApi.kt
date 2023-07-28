package dev.toke.ameplus.network

import dev.toke.ameplus.models.Part
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.models.ProductPart
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface AMEPartsApi {
    @GET("Parts/{partNumber}/ImagesAndNotes")
    suspend fun getParts(@Path("partNumber") partNumber: String, @Header("Authorization") token: String): PartsData?

    @GET("Parts")//?partNumber={partNumber}
    suspend fun getPartByPartNumber(@Query("partNumber") partNumber: String, @Header("Authorization") token: String): ProductPart?
}