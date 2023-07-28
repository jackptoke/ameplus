package dev.toke.ameplus.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SortCounter(
    val planDetailId: Long,
    val bundleMax: Int,
    var bundleCount: Int,
    var updateStamp: LocalDateTime,
    val tubSequence: Int,
    val tubId: Long,
    val processLevel: Int
    )
