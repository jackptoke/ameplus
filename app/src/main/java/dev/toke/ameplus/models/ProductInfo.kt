package dev.toke.ameplus.models

data class ProductInfo(
    val barcodeText: String = "",
    val isActive: Boolean = false,
    val labelQuantity: Int = 0,
    val lastPrinted: String = "",
    val lastPrintedBy: String = "",
    val partNumber: String = "",
    val productCodeId: Int = 0,
    val quantity: Int = 0,
    val revision: String = "",
    val workOrderID: Int = 0
)