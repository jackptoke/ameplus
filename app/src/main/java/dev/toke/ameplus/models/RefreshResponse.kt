package dev.toke.ameplus.models

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String
)