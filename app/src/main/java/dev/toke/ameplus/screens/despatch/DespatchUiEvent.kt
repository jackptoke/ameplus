package dev.toke.ameplus.screens.despatch

import dev.toke.ameplus.data.ScanUiEvent

sealed class DespatchUiEvent {
    data class ScanEvent(val barcode: String): DespatchUiEvent()
//    data class ResetAndScanEvent(val barcode: String): DespatchUiEvent()
}