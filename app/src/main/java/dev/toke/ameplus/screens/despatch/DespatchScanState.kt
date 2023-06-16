package dev.toke.ameplus.screens.despatch

import dev.toke.ameplus.models.ProductInfo
import dev.toke.ameplus.models.ProductPart

data class DespatchScanState(
    var partFromProductNumber: ProductPart? = null,
    var partFromBarcodeText: ProductInfo? = null,
    var isLoading: Boolean = false
)
