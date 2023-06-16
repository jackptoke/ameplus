package dev.toke.ameplus.screens.warehouse

import dev.toke.ameplus.data.ScanUiEvent
//import dev.toke.ameplus.models.PartsData

data class ScanAndMatchState(
    val firstBarcode: String = "",
    val secondBarcode: String = "",
    val scanEvent: ScanUiEvent = ScanUiEvent.FirstScanEvent(barcode = "")
)
