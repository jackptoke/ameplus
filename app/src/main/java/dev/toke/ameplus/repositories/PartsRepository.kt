package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.network.AMEPartsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartsRepository @Inject constructor(private val partApi: AMEPartsApi) {
    private val partsDto = DataOrException<PartsData, Boolean, Exception>()

    suspend fun getParts(partNumber: String): DataOrException<PartsData, Boolean, Exception> {
      try {
          partsDto.loading = true
          partsDto.data = partApi.getPart(partNumber)

          if(partsDto.data.toString().isNotEmpty()) partsDto.loading = false
          Log.d("PartsRepository", "getParts - data - ${ (partsDto.data as PartsData).toString() }")
      }catch(exception: Exception) {
          partsDto.exception = exception
          Log.d("PartRepository", "getParts: - exception - ${exception.localizedMessage}")
      }
        return partsDto
    }
}