package dev.toke.ameplus.models

import kotlinx.serialization.Serializable

@Serializable
data class SortingTub(
    val tubId: Int,
    val planId: Int,
    val batchId: Int,
    val harness: String,
    val revision: String,
    val tubNumber: Int,
    val tubSequence: Int,
    val tubCount: Int,
    val numberOfItemsExpected: Int,
    val circuitCount: Int,
    val tubVariation: String = "",
    val sortTypeId: Int,
    val sortType: Int,
    val sortGroup: Int,
    val processLevel: Int,
    val netWeight: Double
)
