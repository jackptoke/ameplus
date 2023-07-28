package dev.toke.ameplus.models

import kotlinx.serialization.Serializable

@Serializable
data class PlanDetail(
    val planDetailId: Long,
    val batchId: Int,
    val planId: Int,
    val harness: String,
    val revision: String?,
    val revisionId: Int?,
    val quantity: Int,
    val maximumSizePerBundle: Int,
    val totalBundles: Int,
    val circuit: String?,
    val type: String?,
    val cable: String,
    val description: String,
    val tubVariation: String
)
