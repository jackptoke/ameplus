package dev.toke.ameplus.screens.despatch

sealed class DespatchScanResult<T>(val data: T? = null) {
    class BarcodeTextSuccess<T>(data: T? = null): DespatchScanResult<T>(data)
    class PartNumberSuccess<T>(data: T? = null): DespatchScanResult<T>(data)
    class NotFound<T>: DespatchScanResult<T>()
    class Error<T>: DespatchScanResult<T>()
}