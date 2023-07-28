package dev.toke.ameplus.repositories

import android.util.Log
import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.dtos.TubAndPlanDetail
import dev.toke.ameplus.models.SortCounter
import dev.toke.ameplus.network.SortingApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.HttpException
import javax.inject.Inject

class SortingRepositoryImpl @Inject constructor(private val sortingApi: SortingApi, private val authRepo: AuthRepositoryImpl) : SortingRepository {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getPlanAndTubDetail(planDetailId: Long) = GlobalScope.async {
        val tubAndPlanDetail = DataOrException<TubAndPlanDetail, Boolean, Exception>()
        try {
            tubAndPlanDetail.loading = true
            tubAndPlanDetail.data = authRepo.accessToken.value?.let {
                val result = sortingApi.getPlanAndTubDetail(planDetailId, token = "Bearer ${it.token}" )
                result.data as? TubAndPlanDetail
            }
            tubAndPlanDetail.loading = false
        } catch(exception: HttpException) {
            tubAndPlanDetail.exception = exception
            tubAndPlanDetail.loading = false
            Log.d("SortingRepository", "getPlanAndTubDetailId - message: ${exception.message()}")
        }
        tubAndPlanDetail
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getSortCounter(tubId: Int, planDetailId: Long) = GlobalScope.async {
        TODO("Not yet implemented")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun addSortCounter(sortCounter: SortCounter) = GlobalScope.async {
        TODO("Not yet implemented")
    }

}