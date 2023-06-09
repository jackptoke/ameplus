package dev.toke.ameplus.models

data class Part(
    val `external`: Boolean,
    val imagePath: String,
    val partDescription: String,
    val partId: Int,
    val partNumber: String,
    val partTitle: String,
    val qualityCheck: Boolean,
    val qualityNote: String
)