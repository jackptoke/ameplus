package dev.toke.ameplus.network

import dev.toke.ameplus.models.ProductCode
import dev.toke.ameplus.models.ProductInfo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ProductApi {
    @GET("ProductCodes/{barcodeText}")
    suspend fun getProductCode(@Path("barcodeText") barcodeText: String, @Header("Authorization") token: String): ProductCode

    @GET("ProductCodes/{barcodeText}/ProductInfo")
    suspend fun getProductInfo(@Path("barcodeText") barcodeText: String, @Header("Authorization") token: String): ProductInfo
}