package dev.toke.ameplus.data

sealed class ScanResult<T>(val data: T? = null) {
    class Success<T>(data: T? = null): ScanResult<T>(data)
    class NotFound<T>: ScanResult<T>()
    class Error<T>: ScanResult<T>()
}