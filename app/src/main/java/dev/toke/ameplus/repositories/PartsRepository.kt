package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.Part
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.models.ProductPart
import dev.toke.ameplus.network.AMEPartsApi
import dev.toke.ameplus.network.AmePlusApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartsRepository @Inject constructor(private val partApi: AMEPartsApi, private val authRepo: AuthRepositoryImpl) {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getParts(partNumber: String) = GlobalScope.async {
        val partsDto = DataOrException<PartsData, Boolean, Exception>()
        Log.d("PartsRepository", "Part: ${URLEncoder.encode(partNumber, "UTF-8")}")
        try {
          partsDto.loading = true
          partsDto.data = authRepo.accessToken.value?.let { partApi.getParts(partNumber, token = "Bearer ${it.token}") }
          Log.d("PartsRepository", "Part: ${URLEncoder.encode(partNumber, "UTF-8")} Result: ${partsDto.data?.toString()}")
          if(partsDto.data != null) {
              Log.d("PartsRepository", "Part: $partNumber - Positive - ${ (partsDto.data as PartsData).toString() }")
          }
          partsDto.loading = false
      }catch(exception: Exception) {
          partsDto.exception = exception
          partsDto.loading = false
          Log.d("PartRepository", "getParts: - exception - ${exception.localizedMessage}")
      }
        partsDto
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getPart(partNumber: String) = GlobalScope.async {
        val partDto = DataOrException<ProductPart, Boolean, Exception>()
        try {
            partDto.loading = true
            partDto.data = authRepo.accessToken.value?.let {
                Log.d("PartsRepository", "getPart - partNumber - $partNumber")
                Log.d("PartsRepository", "getPart - token - ${it.token}")
                val result = partApi.getPartByPartNumber(partNumber, token = "Bearer ${it.token}")
                Log.d("PartsRepository", "getPart - result - ${result.toString()}")
                result
            }
            partDto.loading = false
        }catch(ex: Exception) {
            Log.d("PartsRepository", "getPart - exception - ${ex.localizedMessage}")
            partDto.exception = ex
            partDto.loading = false
        }
        Log.d("DespatchViewModel", "CheckBarcode - data - ${partDto.data.toString()}")
        partDto
    }
}


