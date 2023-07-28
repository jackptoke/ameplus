package dev.toke.ameplus.dtos

import dev.toke.ameplus.models.PlanDetail
import dev.toke.ameplus.models.SortingTub

data class TubAndPlanDetail(
    val planDetail: PlanDetail,
    val sortingTub: SortingTub
)
