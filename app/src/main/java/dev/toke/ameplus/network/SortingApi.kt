package dev.toke.ameplus.network

import dev.toke.ameplus.dtos.ResponseDto
import dev.toke.ameplus.models.SortCounter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface SortingApi {
    @GET("Sorting/{planDetailId}")
    suspend fun getPlanAndTubDetail(@Path("planDetailId") planDetailId: Long, @Header("Authorization") token: String) : ResponseDto
    @GET("Sorting/Counter/{tubId}/PlanDetails/{planDetailId")
    suspend fun getSortCounter(@Path("tubId") tubId: Int, @Path("planDetailId") planDetailId: Long, @Header("Authorization") token: String) : ResponseDto
    @POST("Sorting")
    suspend fun addSortCounter(@Body sortCounter: SortCounter, @Header("Authorization") token: String) : ResponseDto
}