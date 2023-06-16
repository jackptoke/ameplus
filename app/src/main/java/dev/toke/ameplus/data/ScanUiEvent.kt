package dev.toke.ameplus.data

sealed class ScanUiEvent {
    data class FirstScanEvent(val barcode: String): ScanUiEvent()
    data class SecondScanEvent(val barcode: String): ScanUiEvent()
    data class ScanResetEvent(val barcode: String): ScanUiEvent()
}