package dev.toke.ameplus.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductCode(
    val bagLabelPrinted: String,
    val bagLabelPrintedBy: String,
    val bagLabelQuantity: Int,
    val bagLabelRepintApprovedBy: String,
    val bagLabelRequired: Boolean,
    val barcodeText: String,
    val insertStamp: String,
    val insertUser: String,
    val isActive: Boolean,
    val isMissing: Boolean,
    val labelQuantity: Int,
    val lastPrinted: String,
    val lastPrintedBy: String,
    val markedInactiveBy: String,
    val markedInactiveOn: String,
    val markedInactiveReason: String,
    val missingComponentRecord: Boolean,
    val parentProductCode: Int,
    val partMasterID: Int,
    val productCodeID: Int,
    val productLabelFormatID: Int,
    val productQuantity: Int,
    val productUOM: String,
    val replacementProductCode: Int,
    val reprintApprovedBy: String,
    val reprintReason: String,
    val revisionLevelID: Int,
    val updateStamp: String,
    val updateUser: String,
    val workOrderID: Int
)