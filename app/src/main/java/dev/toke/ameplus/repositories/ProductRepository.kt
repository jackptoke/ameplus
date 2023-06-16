package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.models.ProductCode
import dev.toke.ameplus.models.ProductInfo
import dev.toke.ameplus.network.AmePlusApi
import dev.toke.ameplus.network.ProductApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productApi: ProductApi, private val authRepo: AuthRepositoryImpl) {

    suspend fun getPartCode(barcodeText: String) = GlobalScope.async {
       var productDto = DataOrException<ProductCode, Boolean, Exception>()
        try {
            productDto.loading = true
            productDto.data = authRepo.accessToken.value?.let {
                productApi.getProductCode(barcodeText = barcodeText, token = "Bearer ${it.token}")
            }
            checkNotNull(productDto.data)
            productDto.loading = false
        }catch(ex: Exception) {
            productDto.exception = ex
            productDto.loading = false
        }
        productDto
    }

    suspend fun getPartInfo(barcodeText: String) = GlobalScope.async {
        var productDto = DataOrException<ProductInfo, Boolean, Exception>()
        try {
            productDto.loading = true
            productDto.data = authRepo.accessToken.value?.let {
                productApi.getProductInfo(barcodeText = barcodeText, token = "Bearer ${it.token}")
            }
            checkNotNull(productDto.data)
            productDto.loading = false
        }catch(ex: Exception) {
            productDto.exception = ex
            productDto.loading = false
        }
        productDto
    }
}