package dev.toke.ameplus.repositories

import dev.toke.ameplus.data.DataOrException
import dev.toke.ameplus.dtos.TubAndPlanDetail
import dev.toke.ameplus.models.SortCounter
import kotlinx.coroutines.Deferred

interface SortingRepository {
    suspend fun getPlanAndTubDetail(planDetailId: Long) : Deferred<DataOrException<TubAndPlanDetail, Boolean, Exception>>
    suspend fun getSortCounter(tubId: Int, planDetailId: Long) : Deferred<DataOrException<SortCounter, Boolean, Exception>>
    suspend fun addSortCounter(sortCounter: SortCounter) : Deferred<DataOrException<SortCounter, Boolean, Exception>>
}