package dev.toke.ameplus.screens.sorting

import dev.toke.ameplus.data.ScanUiEvent
import dev.toke.ameplus.models.PlanDetail
import dev.toke.ameplus.models.SortingTub

data class SortingState(
    val planDetailId: Long?,
    val tubId: Int?,
    val planDetail: PlanDetail?,
    val sortingTub: SortingTub?,
    val scanEvent: ScanUiEvent? = ScanUiEvent.FirstScanEvent(barcode = "")
)
