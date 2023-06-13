package dev.toke.ameplus.models

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val expiration: String,
    val message: String,
    val name: String,
    val refreshToken: String,
    val statusCode: Int,
    val token: String,
    val username: String
)