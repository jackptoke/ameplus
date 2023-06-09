package dev.toke.ameplus.models

data class AuthResponse(
    val expiration: String,
    val message: String,
    val name: String,
    val refreshToken: String,
    val statusCode: Int,
    val token: String,
    val username: String
)